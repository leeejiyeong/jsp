package co.micol.prj.notice.command;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class noticeAddAjax implements Command {
	public boolean isMultiRequest(HttpServletRequest request) {
		String contentType = request.getHeader("Content-Type");
		System.out.println(contentType);
		if(contentType != null && contentType.indexOf("multipart/form-data") != -1) {	//-1이라는것은 content-type이 있다는거(?)
			return true;
		}
		return false;
	}
	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		if (isMultiRequest(request)) {	//multipart 요청일때
			NoticeService dao = new NoticeServiceImpl();
			NoticeVO vo = new NoticeVO();
			
			String saveDir = request.getServletContext().getRealPath("/attach/");		//현재 프로젝트 경로
			int maxSize = 1024 * 1024 * 1024;		//최대 10mb까지 업로드
			
			String json = null;
			
			try {			//파일을 업로드시 request객체를 대체함. request는 못읽어내기때문에 multi사용
				MultipartRequest multi = new MultipartRequest
						(request, saveDir, maxSize, "utf-8", new DefaultFileRenamePolicy());
				
				vo.setNoticeWriter(multi.getParameter("noticeWriter"));
				vo.setNoticeDate(Date.valueOf(multi.getParameter("noticeDate")));
				vo.setNoticeTitle(multi.getParameter("noticeTitle"));
				vo.setNoticeSubject(multi.getParameter("noticeSuject"));
				
				String ofileName = multi.getOriginalFileName("nfile");
				String pfileName = multi.getFilesystemName("nfile");
				
				if(ofileName != "") {
					vo.setNoticeFile(ofileName);
					pfileName = saveDir + pfileName;		//저장directory와 저장명
					vo.setNoticeFileDir(pfileName);
				}
				
				int n = dao.noticeInsert(vo);
				
				if(n != 0) {
					//{"retCode":"Success"}
					json ="{\"retCode\":\"Success\"}";
				}else {
					//{"retCode":"Fail"}
					json ="{\"retCode\":\"Fail\"}";
				}
				
			}catch(IOException e){
				e.printStackTrace();
			}
			
			return "Ajax;" + json;
			
		} else {	//multipart요청이 아닐때 
			NoticeVO vo = new NoticeVO();
			vo.setNoticeWriter(request.getParameter("writer"));
			vo.setNoticeTitle(request.getParameter("title"));
			vo.setNoticeSubject(request.getParameter("subject"));
			vo.setNoticeDate(Date.valueOf(request.getParameter("noticeDate")));

			NoticeService service = new NoticeServiceImpl();
			service.noticeInsert(vo);

			String json = null;
			ObjectMapper mapper = new ObjectMapper();
			try {
				json = mapper.writeValueAsString(vo);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return "Ajax:" + json;
		}//=> end of if(isMultipart)
	}

}
