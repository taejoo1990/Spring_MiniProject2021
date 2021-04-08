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

	//login�������� ���Թ޾Ƽ�-> login.jsp�� ���������� �ٽ� login_pro�� ������ϴ� �ͼ�-> �Ʒ����� ��ȿ���˻�
	//?fail=false�� �ٿ����� ���¸� �Ű������� ����������
	
	@GetMapping("/login")
	public String login(@ModelAttribute("temploginUserBean") UserBean temploginUserBean,
			           @RequestParam(value="fail", defaultValue="false")boolean fail,Model model) {

		//fail�� true�� ������ �������� : ���� : �α��� ���� ----���� �õ��ؼ� ������������ ����ֱ� ���� ��Ʈ�ѷ� ����.
		//fail�� false�� ������ ���к��� : ���� : �α��� ����
		model.addAttribute("fail", fail); //---> ó������ fail=false�̹Ƿ�, ���оƴ� ����â�̶� �����ؼ� ����â �ȶ���. 
		
		return "user/login";
	}
	

	//���Խ� ��ȿ���˻�
	@PostMapping("/login_pro")
	public String login_pro(@Valid @ModelAttribute("temploginUserBean") UserBean temploginUserBean,BindingResult result) {

		if(result.hasErrors()) {
			
			return "user/login";
		}	
		
		//��ȿ���˻�����Ѱ��, 2�������� �α����� ������, �� ȸ�����ԵǾ��־ �α��λ��°� (�ѹ߾ռ�)true�� �� �༮������ ���� �˻簡 �ʿ��ϴ�. 
		
		userService.getLoginUserInfo(temploginUserBean);//true or false�� �������� �޼��� ȣ��.
		
		//����ͼ� ���õ� ����� ���� �Ǵ�.
		if(loginUserBean.isUserlogin()==true) {
			return "user/login_success"; //2��������� �Ǿ�� success�� ������ ����.
		}
		return "user/login_fail";
	}
	
	@GetMapping("/not_login")
	public String not_login() {
		
		
		return "user/not_login";
	}
	
	
	

	@GetMapping("/logout")
	public String logout() {

	//�α��λ��°� true��� �����Ͽ�, �α��λ��¸� false�� �ٲٰ� �α׾ƿ��������� ����.
		loginUserBean.setUserlogin(false);
		
		return "user/logout";
	}

	
	
	//================= MODIFY_USER =========================================================================
	
	//ž�޴��ٿ��� '��������'�� ������, �� ��Ʈ�ѷ��� �Ѿ���µ�, �ϴ� modifyUserBean�̶�� ���� �ϳ� ������ �Ѿ���ε�, �Ѿ������ ���⼭ ���� DB������ ���� ����.(���񽺷κ��� ����´�)
	//�α����ؼ� ������ ������ �����̹Ƿ�, modify_user.jsp�� ó�� �������� DB������ ������ �����ؾ��Ѵ�. 
	@GetMapping("/modify_user")
	public String modify_user(@ModelAttribute("modifyUserBean") UserBean modifyUserBean) {

		//ó�� ������������������ ������ �������ϹǷ�, ���񽺿� �ִ� getModifyUserInfo������, id/name/idx�� �˰��ִ� ���°� �ȴ�. �� ���·� ������ ���۵Ǿ���Ѵ�. 
		userService.getModifyUserInfo(modifyUserBean);
		
		return "user/modify_user";
	}


	
	
	
	//UserBean�� ���� ��ȿ�� �˻縦 �����ϸ�, ä�������� �س��Ҵ� id, �̸��� ������ �� ���ư��� ������ �����.
	//��ܿ��� disabled�� �ƴ϶�, readonly(disabled="true"+read)�ؾ��Ѵ�.(disabled�� �س����� ���� ������Ƿ�, read�� �����ϰ��ؾ���)
	
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyUserBean") UserBean modifyUserBean, BindingResult result) {

		if (result.hasErrors()) {

			return "user/modify_user";// ����Ȱ��, ���Է��ʿ���

		}
		
		//������ ���� ���� �Է��� pw�� ���� modifyUserBean�� ������ ���񽺿����� DB��û. 
		userService.getModifyUserInfo(modifyUserBean);
		
		
		return "user/modify_success";//�����, ������������

	}
	
	
	
	//======================= JOIN ======================================================================
	
	// join���� ���ʿ��Դٰ� �����±׸� ����ٽõ��⶧����, ���⼭���� ������ �־���Ѵ�.
	@GetMapping("/join")
	public String join(@ModelAttribute("joinUserBean") UserBean joinUserBean) {

		return "user/join";
	}

	// ��ȿ�� �˻��̹Ƿ� Binding result ���;��Ѵ�
	// �������� joinUserBean���� ������� �ѱ���̹Ƿ� �𵨾�Ʈ����Ʈ �����Ұ���.
	// �������� ��ȿ���˻縦 �ϵ��� ������̼Ǽ����س������Ƿ� @Valid�� �ʿ��ϴ�. 
	@PostMapping("/join_pro")
	public String join(@Valid @ModelAttribute("joinUserBean") UserBean joinUserBean, BindingResult result) {

		if (result.hasErrors()) {

			return "user/join";// ����Ȱ��

		}
		// ��ȿ���˻翡 ��������������
		userService.addUserInfo(joinUserBean);
		
		
		
		
		
		return "user/join_success";

	}

	
	
	//=================== VALIDATOR-INITBINDER ==========================================================================
	
	// @Valid�� �������� �𵨷��Ͽ� �˻��ϱ� ���� ��������, ��������� ��ȿ���˻�� ������̼�
	// ��������� ����������̼� ���� : @InitBinder �� ����, ��������� ��ȿ���˻�Ŭ������ ��ü���� ���ε��Ѵ�.
	@InitBinder
	public void initBinder(WebDataBinder binder) {

		UserValidator validator1 = new UserValidator();
		binder.addValidators(validator1);
	}

	/*
	 * @InitBinder:  �ش� Controller�� ������ ��û�� ���� �߰����� ������ �ϰ� ���� �� ����� �� �ִ�.
	 *               ���� ��� ��û ���� InitBinder�� ������ �޼ҵ尡 ����ȴ�.
	 *               Spring Validator�� ��� �� @Valid annotation���� ������ �ʿ��� ��ü�� �������� ���� ������ method�� �������ִ� annotation�̴�.
	 *               
	 *  https://goodgid.github.io/Spring-MVC-InitBinder/             
	 */

}
