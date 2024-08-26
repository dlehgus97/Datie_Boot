package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.datie_boot.entity.DiaryImage;

import java.util.List;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    @Query("SELECT d.imageno, d.imageName FROM DiaryImage d WHERE d.diaryno = :diaryno")
    List<Object[]> findImagenoAndImageNameByDiaryno(int diaryno);

    List<DiaryImage> findByDiaryno(int diaryno);

}
