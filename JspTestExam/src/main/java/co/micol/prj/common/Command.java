package co.micol.prj.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	String exec(HttpServletRequest request, HttpServletResponse response);
	//수행해서 결과페이지를 찾아주는거. 결과페이지는 string이니까 string으로 적어주는것
}
