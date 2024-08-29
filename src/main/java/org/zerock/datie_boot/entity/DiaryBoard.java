package org.zerock.datie_boot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CurrentTimestamp;


import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "diaryboard")
@ToString
public class DiaryBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int boardno;

	private int diaryno;

	private String title;
	private String content;
	@CurrentTimestamp
	private Timestamp writedate;
	private int viewcnt;
	private int likecnt;

	@OneToMany(mappedBy = "boardno")
	private List<DiaryComment> comment;
	
	@ManyToOne
	@JoinColumn(name="userno")
	private User user;
	
}
