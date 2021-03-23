package kr.co.softsoldesk.controller;

import javax.annotation.Resource;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.softsoldesk.beans.UserBean;

@Controller
public class HomeController {
	
	@Resource(name="LoginBean")
	private UserBean loginUserBean;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(UserBean loginUserBean) {
		return "redirect:/main";
	}
}

