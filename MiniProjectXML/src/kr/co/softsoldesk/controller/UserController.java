package kr.co.softsoldesk.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "user/login";	
	}
	
	@GetMapping("/join")
	public String join() {
		return "user/join";
	}
	
	@PostMapping("/join_pro")
	public String Join_pro(@Valid @ModelAttribute("JoinUserBean") UserBean JoinUserbean, BindingResult result) {
		String viewName="user/join_success";
		String reWrite="user/join";
		if(result.hasErrors()) {
			return reWrite;
		}
		userService.addUserInfo(JoinUserbean);
		return viewName;
	}

	@GetMapping("/modify")
	public String modify() {
		return "user/modify";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "user/logout";
	}
	
	
	
	
}
