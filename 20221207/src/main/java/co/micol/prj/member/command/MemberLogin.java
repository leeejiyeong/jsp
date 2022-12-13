package co.micol.prj.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.micol.prj.common.Command;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.service.MemberVO;
import co.micol.prj.member.serviceImpl.MemberServiceImpl;

public class MemberLogin implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		MemberService dao = new MemberServiceImpl();
		MemberVO vo = new MemberVO();
		HttpSession session = request.getSession();
		
		vo.setMemberId(request.getParameter("memberId"));
		vo.setMemberPassword(request.getParameter("memberPassword"));

		vo = dao.memberSelect(vo);
		String message = null;
		if(vo != null) {
			session.setAttribute("id", vo.getMemberId());
			session.setAttribute("author", vo.getMemberAuthor());
			session.setAttribute("name", vo.getMemberName());

			message = vo.getMemberName() + "님 환영합니다.";
			
			request.setAttribute("message", message);
//			request.setAttribute("member", vo);
		}else {
			message = "아이디 또는 비밀번호가 틀립니다.";
			request.setAttribute("message", message);
		}
		return "member/memberLogin.tiles";
	}

}
