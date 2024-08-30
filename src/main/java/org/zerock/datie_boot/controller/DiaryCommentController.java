package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.DiaryComment;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.DiaryCommentRepository;
import org.zerock.datie_boot.util.PageMaker;
import org.zerock.datie_boot.util.PageVO;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "http://ec2-13-53-91-123.eu-north-1.compute.amazonaws.com", "http://13.53.91.123"})
@RestController
@RequestMapping("/api/comment")
public class DiaryCommentController {

    @Autowired
    private DiaryCommentRepository commentRepo;


    @GetMapping("/list")
    public PageMaker list(@RequestParam int parent_no, PageVO vo) {
        return new PageMaker(commentRepo.findByBoardno(parent_no, vo.makePageable(0, "commentno")));
    }

    @PostMapping("/regist")
    public Map<String, Object> regist(@RequestBody Map map) {
        User userEntity = new User();
        userEntity.setUserno((Integer) map.get("user_no"));

        DiaryComment commentEntity = new DiaryComment();
        commentEntity.setContent((String) map.get("content"));
        commentEntity.setBoardno((Integer) map.get("parent_no"));
        commentEntity.setUser(userEntity);

        DiaryComment entity = commentRepo.save(commentEntity);
        Map<String, Object> result = new HashMap<>();
        result.put("entity", entity);
        result.put("result", entity == null ? "fail" : "success");
        return result;
    }

    @GetMapping("/delete")
    public Map delete(@RequestParam int no, PageVO vo) {
        commentRepo.deleteById(no);
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        return result;
    }

    @GetMapping("/deleteAll")
    public Map deleteAll(@RequestParam int parent_no, PageVO vo) {
        commentRepo.deleteByBoardno(parent_no);
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        return result;
    }
}
