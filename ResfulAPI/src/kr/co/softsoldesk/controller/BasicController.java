package kr.co.softsoldesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {

	@GetMapping("/test1")
	public String Test1() {
		String viewName="Test1";
		return viewName;
	}
}
