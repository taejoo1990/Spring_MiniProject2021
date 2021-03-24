package kr.co.softsoldesk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.softsoldesk.beans.UserBean;

public class CheckLoginInterceptor implements HandlerInterceptor {

	//로그인여부 판단 autowired가 불가하므로 생성자를 홣용
	private UserBean loginUserBean;
	
	public CheckLoginInterceptor(UserBean LoginUserBean) {
		this.loginUserBean=LoginUserBean;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인이 되어 있지 않으면
		if(loginUserBean.isUser_login()==false) {
			//로긴이 되어있지 않은 상태이므로, 로그인전 경로를 받음
			String contextPath=request.getContextPath();
			//로그인 처리가 안 되어 있으므로, 로그인화면으로 리다이렉트
			response.sendRedirect(contextPath + "/user/not_login");
			//다음단계로 이동하지 않음
			return false; //로그인 전
		}
		return true; //로그인 후
	}


	
	
}
