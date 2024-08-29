package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="diarycomment")
@ToString
public class DiaryComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentno;

	private int boardno;
	private String content;
	@CurrentTimestamp
	private Timestamp writedate;
	
	@ManyToOne
	@JoinColumn(name="userno")
	private User user;
}
