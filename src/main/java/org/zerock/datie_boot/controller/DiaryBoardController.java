package org.zerock.datie_boot.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.datie_boot.dto.DiaryDTO;
import org.zerock.datie_boot.entity.DiaryBoard;
import org.zerock.datie_boot.entity.DiaryComment;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.DiaryBoardRepository;
import org.zerock.datie_boot.repository.DiaryCommentRepository;
import org.zerock.datie_boot.repository.DiaryRepository;
import org.zerock.datie_boot.util.PageMaker;
import org.zerock.datie_boot.util.PageVO;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "http://ec2-13-124-13-145.ap-northeast-2.compute.amazonaws.com", "http://13.124.13.145"})
@RestController
@RequestMapping("/api/diaryboard")
public class DiaryBoardController {
	@Autowired
	private DiaryBoardRepository diaryBoardRepo;

	@Autowired
	private DiaryRepository diaryRepository;
	
	@GetMapping("/list")
	public PageMaker list(PageVO vo) {
		Page<DiaryBoard> page = null;
		if ("all".equals(vo.getSearchType())) {
			page = diaryBoardRepo.findByTitleContainingOrContentContaining(vo.getSearchWord(), vo.getSearchWord(), vo.makePageable());
		} else if ("title".equals(vo.getSearchType())) {
			page = diaryBoardRepo.findByTitleContaining(vo.getSearchWord(), vo.makePageable());
		} else if ("content".equals(vo.getSearchType())) {
			page = diaryBoardRepo.findByContentContaining(vo.getSearchWord(), vo.makePageable());
		} else {
			page = diaryBoardRepo.findAll(vo.makePageable());
		}
		return new PageMaker(page);
	}

	@Transactional
	@PostMapping("/regist")
	public Map<String, Object> regist(@RequestParam Map<String, String> map) {
		// Parsing user_no
		User userEntity = new User();
		userEntity.setUserno(Integer.parseInt(map.get("user_no")));

		// Parsing title and content
		DiaryBoard diaryBoard = new DiaryBoard();
		diaryBoard.setTitle(map.get("title"));
		diaryBoard.setContent(map.get("content"));

		// Parsing and converting selectedDate
		String selectedDateStr = map.get("selectedDate");
		Date date = Date.valueOf(selectedDateStr);

		diaryBoard.setDiarydate(date);
		diaryBoard.setUser(userEntity);

		// Saving the DiaryBoard entity
		DiaryBoard entity = diaryBoardRepo.save(diaryBoard);

		// Preparing response
		Map<String, Object> result = new HashMap<>();
		result.put("entity", entity);
		result.put("result", entity == null ? "fail" : "success");
		return result;
	}
	
	@GetMapping("/view")
	public DiaryBoard view(int no, PageVO vo) {
		DiaryBoard entity = diaryBoardRepo.findById(no).get();
		entity.setViewcnt(entity.getViewcnt()+1);
		diaryBoardRepo.save(entity);
		return entity;
	}

	@GetMapping("/getcompany")
	public ResponseEntity<List<DiaryDTO>> getcompany(int no, PageVO vo) {
		DiaryBoard entity = diaryBoardRepo.findById(no).get();
		int userNo = entity.getUser().getUserno();
		String confirmDate = String.valueOf(entity.getDiarydate());
		List<DiaryDTO> results = diaryRepository.findDiarySummaries(userNo, confirmDate);

		// 만약 데이터가 없을 경우, 204 No Content 상태 코드 반환
		if (results.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(results); // HTTP 200 상태 코드와 함께 JSON 형식으로 데이터 반환
	}
	
	@Autowired
	private DiaryCommentRepository commentRepo;
	
	@Transactional // 안넣으면 댓글삭제가 안됨
	@PostMapping("/delete")
	public Map<String, Object> delete(@RequestBody Map map) {
		diaryBoardRepo.deleteById((Integer)map.get("no"));
		commentRepo.deleteByBoardno((Integer)map.get("no"));
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		return result;
	}
	
	@GetMapping("/edit")
	public DiaryBoard edit(int no, PageVO vo) {
		DiaryBoard entity = diaryBoardRepo.findById(no).get();
		return entity;
	}
	
	@Transactional
	@PostMapping("/update")
	public Map<String, Object> update(@RequestBody Map map) {
		System.out.println("map: " + map);
		
		DiaryBoard diaryBoard = diaryBoardRepo.findById((Integer) map.get("boardno")).get();
		diaryBoard.setTitle((String)map.get("title"));
		diaryBoard.setContent((String)map.get("content"));
		DiaryBoard entity = diaryBoardRepo.save(diaryBoard);

		Map<String, Object> result = new HashMap<>();
		result.put("entity", entity);
		result.put("result", entity == null ? "fail" : "success");
		return result;
	}
	
//	@Transactional
//	@PostMapping("/reply")
//	public Map<String, Object> reply(@RequestParam Map map) {
//		User userEntity = new User();
//		userEntity.setUserno(Integer.parseInt((String)map.get("user_no")));
//
//		DiaryBoard diaryBoard = new DiaryBoard();
//		diaryBoard.setTitle((String)map.get("title"));
//		diaryBoard.setContent((String)map.get("content"));
//
//		// 첨부파일
//		if (file != null && !file.isEmpty()) {
//			// 파일명
//			String org = file.getOriginalFilename();
//			String ext = org.substring(org.lastIndexOf("."));
//			String real = System.currentTimeMillis()+ext;
//			System.out.println(org);
//			System.out.println(real);
//			// 파일저장
//			String path = "D:/upload/"+real;
//			try {
//				file.transferTo(new File(path));
//			} catch (Exception e) {}
//			replyEntity.setFilename_org(org);
//			replyEntity.setFilename_real(real);
//		}
//
//		int gno = Integer.parseInt((String)map.get("gno"));
//		int ono = Integer.parseInt((String)map.get("ono"));
//		replyEntity.setUser(userEntity);
//		replyEntity.setGno(gno);
//		replyEntity.setOno(ono+1);
//		replyEntity.setNested(Integer.parseInt((String)map.get("nested"))+1);
//		diaryBoardRepo.updateOno(gno, ono); // 같은 gno중 ono보다 큰 글 +1 처리
//		ReplyEntity entity = diaryBoardRepo.save(replyEntity);
//		Map<String, Object> result = new HashMap<>();
//		result.put("entity", entity);
//		result.put("result", entity == null ? "fail" : "success");
//		return result;
//	}
}
