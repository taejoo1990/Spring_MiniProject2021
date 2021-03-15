package kr.co.softsoldesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.softsoldesk.service.TestService;

@Controller
public class TestController {

	@Autowired
	private TestService testService;
	
	@GetMapping("/test1")
	public String Test1(Model model) {
		String viewName="Test1";
		String str = testService.testMethod(); //���ڿ�
		
		model.addAttribute("str", str);
		
		return viewName;
	}
	
}
