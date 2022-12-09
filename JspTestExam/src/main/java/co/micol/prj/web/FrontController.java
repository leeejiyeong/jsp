package co.micol.prj.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.MainCommand;
import co.micol.prj.common.Command;
import co.micol.prj.member.command.AjaxMemberIdCheck;
import co.micol.prj.member.command.MemberDelete;
import co.micol.prj.member.command.MemberEdit;
import co.micol.prj.member.command.MemberJoinForm;
import co.micol.prj.member.command.MemberList;
import co.micol.prj.member.command.MemberLogin;
import co.micol.prj.member.command.MemberLoginForm;
import co.micol.prj.member.command.MemberLogout;
import co.micol.prj.member.command.MemberEdit;
import co.micol.prj.member.command.MemberSelect;
import co.micol.prj.member.command.MemberUpdate;
import co.micol.prj.notice.command.NoticeDelete;
import co.micol.prj.notice.command.NoticeEdit;
import co.micol.prj.notice.command.NoticeEditForm;
import co.micol.prj.notice.command.NoticeInsert;
import co.micol.prj.notice.command.NoticeInsertForm;
import co.micol.prj.notice.command.NoticeList;
import co.micol.prj.notice.command.NoticeSelect;
import co.micol.prj.notice.command.NoticeUpdate;
import co.micol.prj.member.command.MemberJoin;

//@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// HashMap
	private HashMap<String, Command> map = new HashMap<String, Command>();

	public FrontController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		// 명령집단 map.put(k,v) 을 보관하는곳 -> 명령집단을 담을수 있게 위에 hashmap을 만들어줘야한다.
		map.put("/main.do", new MainCommand()); // 처음 실행하는 페이지 - 위에서 설정한 hashmap을 이용하여 main.do는 maincommand 메소드가 실행되게
		map.put("/memberList.do", new MemberList()); // 멤버목록 보기 - memberList.do는 memberList가 실행되게한다.
		map.put("/memberJoinForm.do", new MemberJoinForm()); // 회원가입폼
		map.put("/AjaxMemberIdCheck.do", new AjaxMemberIdCheck()); // 회원아이디 중복체크
		map.put("/memberJoin.do", new MemberJoin()); // 회원가입 처리
		map.put("/memberLoginForm.do", new MemberLoginForm()); 	//로그인폼 호출
		map.put("/memberLogin.do", new MemberLogin());	//로그인 처리
		map.put("/memberLogout.do", new MemberLogout()); 	//로그아웃 처리
		map.put("/memberSelect.do", new MemberSelect());	//맴버 한명 조회
		map.put("/memberEdit.do", new MemberEdit());		//멤버 수정폼 호출
		map.put("/memberDelete.do", new MemberDelete());		//맴버 삭제
		map.put("/memberUpdate.do", new MemberUpdate());		//맴버 수정
		map.put("/noticeInsertForm.do", new NoticeInsertForm());	//공지사항 등록 폼
		map.put("/noticeList.do", new NoticeList());	//공지사항 목록
		map.put("/noticeSelect.do", new NoticeSelect());	//공지사항 상세보기(목록에서 선택)
		map.put("/noticeInsert.do", new NoticeInsert());	//공지사항 등록
		map.put("/noticeEditForm.do", new NoticeEditForm());	//공지사항 수정 폼
		map.put("/noticeUpdate.do", new NoticeUpdate());	//공지사항 수정
		map.put("/noticeEdit.do", new NoticeEdit());		//공지사항 수정
		map.put("/noticeDelete.do", new NoticeDelete());	//공지사항 삭제
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 요청을 분석, 실행, 결과를 돌려주는곳

		request.setCharacterEncoding("utf-8"); 		// 한글깨짐방지
		String uri = request.getRequestURI(); 		// 1. uri값을 읽어온다
		String contextPath = request.getContextPath(); 		// 2. 그 중에서 ContextPath를 읽어온다
		String page = uri.substring(contextPath.length()); 		// 3. 실제 요청명(index.jsp)을 읽어내기 위해 uri에서 contextPath만큼의 길이를 뺀
																// 위치부터 헤아린다.

		// 위에 3개의 String이
		// ex) localhost/20221206/index.jsp에서
		// uri(/20221206/index.jsp) contextPath(/20221206)

		Command command = map.get(page); 		// map안의 키 값을 command로 찾고
		String viewPage = command.exec(request, response); 		// 찾은 Command를 실행시켜서 그 결과를 받고

		// view resolve start (그 결과를 어떤 페이지에 뿌려줄것인가)
		if (!viewPage.endsWith(".do")) { 		// viewpage가 .do로 끝나지 않는다면
			if (viewPage.startsWith("Ajax:")) { 		// ajax로 시작하는지 확인
				// ajax
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print(viewPage.substring(5));// Ajax: <-(총 5자) 뒤부터 헤아려서 쓰라는거임
				return;
			} else if (!viewPage.endsWith(".tiles")) {
				viewPage = "WEB-INF/views/" + viewPage + ".jsp"; 	// 타일즈 적용 안하는것
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect(viewPage);
		}
		// view resolve end
	}

}
