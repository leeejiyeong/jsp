package co.micol.prj.notice.command;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import co.micol.prj.common.Command;
import co.micol.prj.notice.service.NoticeAttachVO;
import co.micol.prj.notice.service.NoticeService;
import co.micol.prj.notice.service.NoticeVO;
import co.micol.prj.notice.serviceImpl.NoticeServiceImpl;

public class NoticeUpdate implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// 공지사항 수정
		NoticeService dao = new NoticeServiceImpl();
		NoticeVO vo = new NoticeVO();
		
		String saveDir = request.getServletContext().getRealPath("//attach/");		//현재 프로젝트 경로
		int maxSize = 1024 * 1024 * 1024;		//최대 10mb까지 업로드
		
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
				vo.setNoticeFile(pfileName);
				pfileName = saveDir + pfileName;		//저장directory와 저장명
				vo.setNoticeFileDir(pfileName);
			}
			
			int n = dao.noticeInsert(vo);
			if(n != 0) {
				request.setAttribute("message", "공지사항이 수정되었습니다");
			}else {
				request.setAttribute("message", "공지사항 수정에 실패했습니다");
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return "notice/noticeMessage.tiles";
	}
}
