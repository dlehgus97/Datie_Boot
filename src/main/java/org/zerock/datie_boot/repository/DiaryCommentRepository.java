package org.zerock.datie_boot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.datie_boot.entity.DiaryComment;

public interface DiaryCommentRepository extends JpaRepository<DiaryComment, Integer>{
	public Page<DiaryComment> findByBoardno(int parent_no, Pageable page);
	public void deleteByBoardno(int parent_no);
}
