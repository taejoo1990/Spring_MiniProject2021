package kr.co.softsoldesk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.softsoldesk.beans.UserBean;

@Controller
public class HomeController {
	
	//@Resource(name="loginUserBean")
	//private UserBean loginUserBean; // 객체 생성
	
	
	
	//클라이언트가 어디서든 주소를 요청하면  String Home()을 호출함
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String Home(HttpServletRequest request) {
		//System.out.println(loginUserBean);
		System.out.println(request.getServletContext().getRealPath("/"));
		
		return "redirect:/main";
	}

}
