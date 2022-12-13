package co.micol.prj.notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class NoticeEditForm implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// 회원수정을 위한 데이터 호출후 수정폼에 전달
		NoticeService dao = new NoticeServiceImpl();
		NoticeVO vo = new NoticeVO();

		vo.setNoticeId(Integer.parseInt(request.getParameter("noticeId")));
		vo = dao.noticeSelect(vo);
		request.setAttribute("notice", vo);

		return "notice/noticeEditForm.tiles";
	}
}
