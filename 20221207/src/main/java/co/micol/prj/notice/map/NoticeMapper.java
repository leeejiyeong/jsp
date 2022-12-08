package co.micol.prj.notice.map;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.micol.prj.notice.service.NoticeAttachVO;
import co.micol.prj.notice.service.NoticeVO;

public interface NoticeMapper {
	List<NoticeVO> noticeSelectList();
	List<NoticeVO> noticeSelect(NoticeVO vo);		//상세조회 = 첨부파일이 여러개 있으면 리스트 형태이기 때문에 이렇게 적어준다 
	int noticeInsert(NoticeVO vo); 		//게시글 저장
	int noticeUpdate(NoticeVO vo); 		//게시글 수정
	int noticeDelete(NoticeVO vo); 		//게시글 삭제
	
	int noticeAttachInsert(NoticeAttachVO vo);		//첨부파일 저장
	int noticeAttachDelete(NoticeAttachVO vo);		//첨부파일 삭제
	
	List<NoticeVO> noticeSearchList(@Param("key") String key, @Param("val") String val);		//게시글 내 검색을 위해
}
