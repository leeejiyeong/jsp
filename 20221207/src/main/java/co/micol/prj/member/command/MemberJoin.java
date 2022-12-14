package co.micol.prj.member.command;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.common.AES256Util;
import co.micol.prj.common.Command;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.service.MemberVO;
import co.micol.prj.member.serviceImpl.MemberServiceImpl;

public class MemberJoin implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		// 회원가입 처리
		MemberService dao = new MemberServiceImpl();
		MemberVO vo = new MemberVO();
		String password = request.getParameter("memberPassword");
		try {
			AES256Util aes = new AES256Util();
			password = aes.encrypt(password);	//암호화 됨
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (GeneralSecurityException e) {
			e.printStackTrace();
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println(password +"============");
		
		int n = 0;
//		String viewPage = null;		//돌려줄 페이지
		String message = null;		//메시지를
		
		vo.setMemberId(request.getParameter("memberId"));
		vo.setMemberName(request.getParameter("memberName"));
		vo.setMemberPassword(password);
		if(request.getParameter("memberAge") != "") {
			vo.setMemberAge(Integer.valueOf(request.getParameter("memberAge")));
		}	// 폼에서 넘어오는건 다 문자이기 때문에 변환필요
		vo.setMemberAddress(request.getParameter("memberAddress"));
		vo.setMemberTel(request.getParameter("memberTel"));
		vo.setMemberAuthor("USER");
		
		n = dao.memberInsert(vo);
		if(n != 0) {
			message = "회원가입이 성공적으로 처리되었습니다.";
			//return "memberList.do";
		}else {
			message = "회원가입이 실패했습니다.";
		}
		request.setAttribute("message", message);
		return "member/memberJoin.tiles";
	}

}
