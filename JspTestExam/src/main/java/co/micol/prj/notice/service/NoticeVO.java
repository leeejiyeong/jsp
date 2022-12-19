package co.micol.prj.notice.service;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeVO {
	private int noticeId;
	private String noticeWriter;
	
	@JsonFormat(pattern="yyyy-MM-dd", locale="Asia/Seoul")
	private Date noticeDate;
	private String noticeTitle;
	private String noticeSubject;
	private int noticeHit;
	
	//join을 위한 확장(큰vo에다가 작은vo들을 적어준다. 겹치는거 제외)
	private int attachId;
	private String noticeFile;
	private String noticeFileDir;
	
	//
}
