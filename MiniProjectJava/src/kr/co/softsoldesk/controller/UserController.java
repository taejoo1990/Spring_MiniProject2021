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
	
	//=================== DI ==========================================================================
	
	
	@Autowired
	private UserService userService;
	
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	
	
	
	
	//===============  LOGIN =============================================================================

	//login에서부터 주입받아서-> login.jsp로 가져갔더니 다시 login_pro로 가라고하니 와서-> 아래에서 유효성검사
	//?fail=false로 붙여가는 형태를 매개변수에 만들어놓았음
	
	@GetMapping("/login")
	public String login(@ModelAttribute("temploginUserBean") UserBean temploginUserBean,
			           @RequestParam(value="fail", defaultValue="false")boolean fail,Model model) {

		//fail에 true가 들어오면 실패인정 : 실패 : 로그인 실패 ----정말 시도해서 실패했을때만 띄워주기 위한 컨트롤러 조작.
		//fail에 false가 들어오면 실패부정 : 성공 : 로그인 성공
		model.addAttribute("fail", fail); //---> 처음에는 fail=false이므로, 실패아닌 성공창이라 설정해서 실패창 안띄운다. 
		
		return "user/login";
	}
	

	//주입시 유효성검사
	@PostMapping("/login_pro")
	public String login_pro(@Valid @ModelAttribute("temploginUserBean") UserBean temploginUserBean,BindingResult result) {

		if(result.hasErrors()) {
			
			return "user/login";
		}	
		
		//유효성검사통과한경우, 2차적으로 로그인이 가능한, 즉 회원가입되어있어서 로그인상태가 (한발앞서)true가 된 녀석인지에 대한 검사가 필요하다. 
		
		userService.getLoginUserInfo(temploginUserBean);//true or false를 물어오라고 메서드 호출.
		
		//물어와서 세팅된 결과에 대한 판단.
		if(loginUserBean.isUserlogin()==true) {
			return "user/login_success"; //2차통과까지 되어야 success로 보내줄 것임.
		}
		return "user/login_fail";
	}
	
	@GetMapping("/not_login")
	public String not_login() {
		
		
		return "user/not_login";
	}
	
	
	

	@GetMapping("/logout")
	public String logout() {

	//로그인상태가 true라는 전제하에, 로그인상태를 false로 바꾸고 로그아웃페이지로 간다.
		loginUserBean.setUserlogin(false);
		
		return "user/logout";
	}

	
	
	//================= MODIFY_USER =========================================================================
	
	//탑메뉴바에서 '정보수정'을 누르면, 이 컨트롤러로 넘어오는데, 일단 modifyUserBean이라는 것을 하나 데리고 넘어갈것인데, 넘어가기전에 여기서 빈이 DB정보를 물고 간다.(서비스로부터 물어온다)
	//로그인해서 수정이 가능한 상태이므로, modify_user.jsp로 처음 갈때부터 DB정보를 물고가서 전달해야한다. 
	@GetMapping("/modify_user")
	public String modify_user(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) {

		//처음 수정페이지갈때부터 정보를 물고가야하므로, 서비스에 있는 getModifyUserInfo돌리면, id/name/idx를 알고있는 상태가 된다. 이 상태로 폼폼이 시작되어야한다. 
		userService.getModifyUserInfo(modifyUserBean);
		
		return "user/modify_user";
	}


	
	
	
	//UserBean에 대한 유효성 검사를 진행하면, 채워지도록 해놓았던 id, 이름이 컴파일 시 날아가는 문제가 생긴다.
	//뷰단에서 disabled가 아니라, readonly(disabled="true"+read)해야한다.(disabled만 해놓으면 값이 사라지므로, read도 가능하게해야함)
	
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyUserBean") UserBean modifyUserBean, BindingResult result) {

		if (result.hasErrors()) {

			return "user/modify_user";// 위배된경우, 재입력필요함

		}
		
		//수정을 위해 새로 입력한 pw가 들어온 modifyUserBean을 가지고 서비스에가서 DB요청. 
		userService.getModifyUserInfo(modifyUserBean);
		
		
		return "user/modify_success";//통과시, 성공페이지로

	}
	
	
	
	//======================= JOIN ======================================================================
	
	// join으로 최초에왔다가 폼폼태그를 갖고다시돌기때문에, 여기서부터 모델주입 있어야한다.
	@GetMapping("/join")
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserBean) {

		return "user/join";
	}

	// 유효성 검사이므로 Binding result 들어와야한다
	// 유저빈을 joinUserBean으로 뷰단으로 넘길것이므로 모델어트리뷰트 생략불가함.
	// 유저빈은 유효성검사를 하도록 어노테이션설정해놓았으므로 @Valid가 필요하다. 
	@PostMapping("/join_pro")
	public String join(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean, BindingResult result) {

		if (result.hasErrors()) {

			return "user/join";// 위배된경우

		}
		// 유효성검사에 위배되지않은경우
		userService.addUserInfo(joinUserBean);
		
		
		
		
		
		return "user/join_success";

	}

	
	
	//=================== VALIDATOR-INITBINDER ==========================================================================
	
	// @Valid로 유저빈을 모델로하여 검사하기 전에 끼워넣을, 사용자정의 유효성검사용 어노테이션
	// 사용자정의 에러어노테이션 설정 : @InitBinder 한 다음, 사용자정의 유효성검사클래스의 객체만들어서 바인딩한다.
	@InitBinder
	public void initBinder(WebDataBinder binder) {

		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}

	/*
	 * @InitBinder:  해당 Controller로 들어오는 요청에 대해 추가적인 설정을 하고 싶을 때 사용할 수 있다.
	 *               또한 모든 요청 전에 InitBinder를 선언한 메소드가 실행된다.
	 *               Spring Validator를 사용 시 @Valid annotation으로 검증이 필요한 객체를 가져오기 전에 수행할 method를 지정해주는 annotation이다.
	 *               
	 *  https://goodgid.github.io/Spring-MVC-InitBinder/             
	 */

}
