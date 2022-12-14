package co.micol.prj.notice.command;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class noticeUpdateAjax implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// 파라미터를 다 읽어들이고 update변수로 사용 => update가 정상적으로 작동하면 => 다시 json으로 반환
		NoticeVO vo = new NoticeVO();
		vo.setNoticeId(Integer.parseInt(request.getParameter("id")));
		vo.setNoticeWriter(request.getParameter("writer"));
		vo.setNoticeTitle(request.getParameter("title"));
		vo.setNoticeDate(Date.valueOf(request.getParameter("date")));
		
		NoticeService service =new NoticeServiceImpl();
		int cnt = service.noticeUpdate(vo);
		
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		if(cnt > 0) {	
			vo = service.noticeSelect(vo);	//수정(변경)처리 후에 ajax가 호출한 콜백함수에 id와 관련된 모든 정보를 다 넘겨줘야 하는데 
										//조회수랑 첨부파일은 비어있으므로 정상적으로 받아오기위해선 select가 필요함
			try {
				json = mapper.writeValueAsString(vo);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return "Ajax:" + json;
		}else {
			return "Ajax:" + "{\"retCode\":\"Fail\"}";
		}
		
	}

}
