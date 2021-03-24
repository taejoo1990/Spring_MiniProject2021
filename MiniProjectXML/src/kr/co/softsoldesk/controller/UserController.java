package kr.co.softsoldesk.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	public String login(@ModelAttribute("TempLoginBean") UserBean tempLoginBean) {
		
		return "user/login";	
	}
	@PostMapping("/login_pro")
	public String login_pro(@Validated @ModelAttribute("TempLoginBean") UserBean tempLoginBean, BindingResult res) {
		String viewName="user/login_fail";
		String reWrite="user/login";
		if(res.hasErrors()) {
			return reWrite; 
			}return viewName;
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
