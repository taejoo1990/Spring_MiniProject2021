package kr.co.softsoldesk.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	

	@GetMapping("/login")
	public String login(@ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean,
			            @RequestParam(value = "fail", defaultValue = "false") boolean fail, Model model) {
		
		
		//fail에 true가 들어오면 실패 인정 : 실패
		// fail에 false가 들어오면 실패부정 : 성공 (즉 로그인 창)
		model.addAttribute("fail", fail);
		
		return "user/login";
	}
	
	
	@PostMapping("/login_pro")
	public String login_pro(@Valid @ModelAttribute("tempLoginUserBean") UserBean tempLoginUserBean, BindingResult result) {
		
		//유효성 통과 유무
		if(result.hasErrors()) {
		
			return "user/login";
		}
		
		// 회원가입 유무
		userService.getLoginUserInfo(tempLoginUserBean); // true, false
		
		if(loginUserBean.isUserlogin()==true) {
			return "user/login_success"; 
		}
		
		
		
		
		return "user/login_failure";
		
	}
	

	@GetMapping("/join") // post 방식으로 들어왔으니 ModelAttribute도 들어와야 함
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserbean) {
		
		return "user/join";
	}
	
	@PostMapping("/join_pro")
	public String join_pro(@Valid @ModelAttribute("joinUserBean")UserBean joinUserBean, BindingResult result) {
		
		if(result.hasErrors()) { 
			return "user/join";
		}
		
		
		userService.addUserInfo(joinUserBean);
		
		
		
		return "user/join_success";
	}
	
	
	@GetMapping("/not_login")
	public String not_login() {
	
		
		return "user/not_login";
	}
	
	
	
	@GetMapping("/modify")
	public String modify(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) {
	
		userService.getModifyUserInfo(modifyUserBean);
		
		
		return "user/modify";
	}
	
	
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyUserBean")UserBean modifyUserBean, BindingResult result) {
		
		
		if(result.hasErrors()) {
			return "user/modify";
		}
		
		userService.modifyUserInfo(modifyUserBean); // 업데이트 완료
		
		
		
		return "user/modify_success";
	}
	
	
	
	
	
	@GetMapping("/logout")
	public String logout() {
		
		loginUserBean.setUserlogin(false);
		
		return "user/logout";
	}
	
	//@Valid(유효성 검사)와 동일하다
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
		
		
	}
	
	
	
	
	
	
	
}
