package kr.co.softsoldesk.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.validator.UserValidator;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/login")
	public String login() {
		return "user/login";	
	}
	
	@GetMapping("/join" )
	public String join(@ModelAttribute("JoinUserBean") UserBean joinUserbean) {
		return "user/join";
	}
	
	@PostMapping("/join_pro")
	public String Join_pro(@Valid @ModelAttribute("JoinUserBean") UserBean joinUserbean, BindingResult result) {
		String viewName="user/join_success";
		String reWrite="user/join";
		if(result.hasErrors()) {
			return reWrite;
		}
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
	
	//커스터마이징한 validator를 controller에 등록한다.
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}

	
	
	
	
}
