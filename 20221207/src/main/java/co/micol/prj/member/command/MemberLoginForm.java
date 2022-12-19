package co.micol.prj.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.common.Command;

public class MemberLoginForm implements Command {

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
<<<<<<< HEAD
=======
		// 로그인 폼 호출
>>>>>>> branch 'main' of https://github.com/leeejiyeong/jsp.git
		return "member/memberLoginForm.tiles";
	}

}
