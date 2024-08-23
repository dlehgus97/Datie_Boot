package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "diaryimage")
public class DiaryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageno;

    private int diaryno;

    @Column(name = "image_name")
    private String imageName;
}
