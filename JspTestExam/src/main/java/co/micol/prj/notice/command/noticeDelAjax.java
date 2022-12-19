package co.micol.prj.notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class noticeDelAjax implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		NoticeVO vo = new NoticeVO();
		vo.setNoticeId(Integer.parseInt(id));

		NoticeService service = new NoticeServiceImpl();
		int delCnt = service.noticeDelete(vo);
		String json = null;
		if (delCnt > 0) {
			// {"retCode":"Success", "id": id}
			json = "{\"retCode\":\"Success\", \"id\": " + id + "}";
		} else {
			// {"retCode":"Fail"}
			json = "{\"retCode\":\"Fail\"}";
		}
		return "Ajax:" + json;
	}

}
