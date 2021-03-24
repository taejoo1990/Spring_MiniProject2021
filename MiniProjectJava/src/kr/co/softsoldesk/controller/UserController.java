package kr.co.softsoldesk.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.UserService;
import kr.co.softsoldesk.validator.UserValidator;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Resource(name = "LoginBean")
	private UserBean loginUserBean;
	
	@GetMapping("/login")
	public String login(@ModelAttribute("TempLoginBean") UserBean tempLoginBean,
			@RequestParam(value = "fail" , defaultValue="false") boolean fail, Model model) {
		
		//fail값에 true가 들어오면 실패. 
		//기본값 fail값에 false가 들어있으면 성공.
		model.addAttribute("fail", fail);
		
		
		
		
		return "user/login";	
	}
	@PostMapping("/login_pro")
	public String login_pro(@Validated @ModelAttribute("TempLoginBean") UserBean tempLoginBean, BindingResult res) {
		String fail="user/login_fail";
		String success="user/login_success";
		String reWrite="user/login";
		userService.getLoginUserInfo(tempLoginBean); //true or false
		if(res.hasErrors()) {
			return reWrite; 
			}
		
		if(loginUserBean.isUser_login()==true) {
			return success;
		}
		return fail;
	}
	
	@GetMapping("/not_login")
	public String notLogin() {
	String viewName="user/not_login";
	return viewName;
	}
	
	@GetMapping("/join" )
	public String join(@ModelAttribute("JoinUserBean") UserBean joinUserbean) {
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
		
		loginUserBean.setUser_login(false);
		return "user/logout";
	}
	
	//커스터마이징한 validator를 controller에 등록한다.
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}
	
	

	
	
	
	
}
