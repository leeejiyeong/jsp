package co.micol.prj.notice.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class noticeListAjax implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//{"name":"홍길동", "age":20} : json포맷
		NoticeService service = new NoticeServiceImpl();
		List<NoticeVO> list = service.noticeSelectList();
		ObjectMapper mapper = new ObjectMapper();
		
		String json = null;
		try {
			json = mapper.writeValueAsString(list);	//자바의 객체를 json형태의 문자열 형태로 바꿔주는 역할
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
		
		return "Ajax:" + json;
	}

}
