package kr.co.softsoldesk.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.service.TopMenuService;

public class TopMenuInterceptor implements HandlerInterceptor {

	//autowired 사용못함.
	private TopMenuService topmenuservice;
	
	public TopMenuInterceptor(TopMenuService topmenuservice) {
		this.topmenuservice = topmenuservice;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		List<BoardInfoBean> Topmenuservice = topmenuservice.getTopMenuList();
		request.setAttribute("topMenuList", Topmenuservice );
		return true;
	}
}
