package kr.co.softsoldesk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.softsoldesk.beans.UserBean;

@Controller
public class HomeController {
	
	//이름+오토와이어드
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String Home(HttpServletRequest request) {
		
		//파일객체는 실제 경로로 지정해야 하기 때문에 application객체를 뽑아서 realPath로 디렉토리를 찾아내야한다. (BoardService에서 path처리할것).

		System.out.println(request.getServletContext().getRealPath("/"));
		
		return "redirect:/main";
	}
	
	
}