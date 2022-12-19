package co.micol.prj.notice.service;

import java.util.List;

public interface NoticeService {
	List<NoticeVO> noticeSelectList();
	NoticeVO noticeSelect(NoticeVO vo);		//첨부파일 한개만 받음
	int noticeInsert(NoticeVO vo); 		//게시글 저장
	int noticeUpdate(NoticeVO vo); 		//게시글 수정
	int noticeDelete(NoticeVO vo); 		//게시글 삭제
	
	int noticeAttachInsert(NoticeAttachVO vo);		//첨부파일 저장
	int noticeAttachDelete(NoticeAttachVO vo);		//첨부파일 삭제
	
	void noticeHitUpdate(int id);		//조회수 증가
	
	List<NoticeVO> noticeSearchList(String key, String val);		//게시글 내 검색을 위해
}
