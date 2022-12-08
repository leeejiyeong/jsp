package co.micol.prj.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.micol.prj.common.Command;

public class MemberLogout implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// 로그아웃 처리
		HttpSession session = request.getSession();
		
		String message = (String)session.getAttribute("name");		//안녕히 가라는 메시지를 보여주고 싶다.
		message += "님 정상적으로 로그아웃 되었습니다. ㅃㅇ";
		
		session.invalidate();
		request.setAttribute("message", message);
		
		return "member/memberLogin.tiles";		//아까 메시지만 보여주는 페이지로 돌아간다
	}

}
