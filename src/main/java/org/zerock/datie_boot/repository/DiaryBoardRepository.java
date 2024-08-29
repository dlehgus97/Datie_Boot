package org.zerock.datie_boot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.datie_boot.entity.DiaryBoard;

public interface DiaryBoardRepository extends JpaRepository<DiaryBoard, Integer>{
	public Page<DiaryBoard> findByTitleContaining(String searchWord, Pageable page);
	public Page<DiaryBoard> findByContentContaining(String searchWord, Pageable page);
	public Page<DiaryBoard> findByTitleContainingOrContentContaining(String searchWord, String searchWord2, Pageable page);
}
