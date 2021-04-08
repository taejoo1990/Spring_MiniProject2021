package kr.co.softsoldesk.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.softsoldesk.beans.UserBean;

@Controller
public class HomeController {
	
	//�̸�+������̾��
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String Home(HttpServletRequest request) {
		
		//���ϰ�ü�� ���� ��η� �����ؾ� �ϱ� ������ application��ü�� �̾Ƽ� realPath�� ���丮�� ã�Ƴ����Ѵ�. (BoardService���� pathó���Ұ�).

		System.out.println(request.getServletContext().getRealPath("/"));
		
		return "redirect:/main";
	}
	
	
}