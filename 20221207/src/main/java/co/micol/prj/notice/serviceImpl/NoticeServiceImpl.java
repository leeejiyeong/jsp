package co.micol.prj.notice.serviceImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.micol.prj.common.DataSource;
import co.micol.prj.notice.map.NoticeMapper;
import co.micol.prj.notice.service.NoticeAttachVO;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;

public class NoticeServiceImpl implements NoticeService {
	private SqlSession sqlsession = DataSource.getInstance().openSession(true);
	private NoticeMapper map = sqlsession.getMapper(NoticeMapper.class);
	
	@Override
	public List<NoticeVO> noticeSelectList() {
		return map.noticeSelectList();
	}

	@Override
	public NoticeVO noticeSelect(NoticeVO vo) {
		noticeHitUpdate(vo.getNoticeId());	//조회수를 증가
		return map.noticeSelect(vo);
	}

	@Override
	public int noticeInsert(NoticeVO vo) {
		return map.noticeInsert(vo);
	}

	@Override
	public int noticeUpdate(NoticeVO vo) {
		return map.noticeUpdate(vo);
	}

	@Override
	public int noticeDelete(NoticeVO vo) {
		return map.noticeDelete(vo);
	}

	@Override
	public int noticeAttachInsert(NoticeAttachVO vo) {
		return map.noticeAttachInsert(vo);
	}

	@Override
	public int noticeAttachDelete(NoticeAttachVO vo) {
		return map.noticeAttachDelete(vo);
	}

	@Override
	public List<NoticeVO> noticeSearchList(String key, String val) {
		return map.noticeSearchList(key, val);
	}

	@Override
	public void noticeHitUpdate(int id) {
		//return map.noticeHitUpdate();
		
	}
}
