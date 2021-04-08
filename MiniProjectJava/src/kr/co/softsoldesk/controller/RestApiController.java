package kr.co.softsoldesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.softsoldesk.service.UserService;

@RestController
public class RestApiController {

	/*
	 * @RestController�� Spring MVC Controller�� @ResponseBody�� �߰��� ���Դϴ�.
	 * �翬�ϰԵ� RestController�� �ֿ뵵�� Json ���·� ��ü �����͸� ��ȯ�ϴ� ���Դϴ�. 
	 * 
	 * 1. Client�� URI �������� �� ���񽺿� ��û�� ������.
       2. Mapping�Ǵ� Handler�� �� Type�� ã�� DispatcherServlet�� ��û�� ���ͼ�Ʈ�Ѵ�.
       3. RestController�� �ش� ��û�� ó���ϰ� �����͸� ��ȯ�Ѵ�.
	 * 
	 * */
	
	
	/*Ŭ�����忡���� ���� :
	 * Ŭ���̾�Ʈ��û->����ƮǮ��Ʈ�ѷ��� �ּҿ� ���� {user_id}��  DB������->����޾ƿͼ�-> boolean�� json���� ��ȯ */
	
	//��, Back���� DB�� �����ѰͰ�, Front���� ���� ��û�� ���⼭ ������-> ����� json���� ��ȯ�ϴ°�!
	
	
	
	
	@Autowired
	private UserService userService;
	
	//Ŭ���̾�Ʈ-> ��Ʈ�ѷ��� �ּҿ� �����͸� ���������� �ٿ����� ���, @PathVariable�� �Ű������� �ٿ� �޾ƾ��Ѵ�.
	//cf) �Ķ�����̸��� ���� ��Ʈ�� �����ڷ� �ٿ� ������쿡�� @RequestParam���� ������ȴ�
	@GetMapping("/user/checkUserIdExist/{user_id}")
	public String checkUserIdExist(@PathVariable String user_id) {
		
		boolean chk=userService.checkUserIdExist(user_id);
		
		
		return chk+"";//������������ "" �ϳ� �ٿ��ذ�
	}
	
}

//https://elfinlas.github.io/2018/02/18/spring-parameter/
