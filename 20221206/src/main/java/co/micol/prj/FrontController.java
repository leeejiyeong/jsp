package co.micol.prj;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.common.Command;
import co.micol.prj.member.command.MemberList;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//hashmap
	private HashMap<String, Command> map = new HashMap<String, Command>();
    
    public FrontController() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		//명령(Command)를 저장하는 영역
		map.put("/main.do", new MainCommand());		//처음페이지 명령
		map.put("/memberList.do", new MemberList()); 	//맴버 목록보기
		
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Controller 본체
		
		request.setCharacterEncoding("utf-8"); 		//한글 깨짐 방지를 위해서 적어준다(post방식으로 오면 깨지기 때문)
		String uri = request.getRequestURI();		//uri값을 읽어온다
		String contextPath = request.getContextPath(); 		//ContextPath를 읽어온다
		String page = uri.substring(contextPath.length()); 		//실제 요청명(index.jsp = uri에서 contextPath만큼의 길이를 뺀 위치부터 셀린다
		
		//위에 3개의 String이
		//ex) localhost/20221206/index.jsp에서 
		// uri(/20221206/index.jsp) contextPath(/20221206)
		
		Command command = map.get(page);		//수행할 command를 찾고
		String viewPage = command.exec(request, response);		//찾은 Command를 실행
		
		//view resolve
		if(!viewPage.endsWith(".do")) {
			viewPage = "WEB-INF/views/" + viewPage + ".jsp";
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect(viewPage);
		}
	}
		
}
