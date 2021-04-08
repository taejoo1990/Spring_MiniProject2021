package kr.co.softsoldesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.co.softsoldesk.service.UserService;

@RestController
public class RestApiController {

	/*
	 * @RestController는 Spring MVC Controller에 @ResponseBody가 추가된 것입니다.
	 * 당연하게도 RestController의 주용도는 Json 형태로 객체 데이터를 반환하는 것입니다. 
	 * 
	 * 1. Client는 URI 형식으로 웹 서비스에 요청을 보낸다.
       2. Mapping되는 Handler와 그 Type을 찾는 DispatcherServlet이 요청을 인터셉트한다.
       3. RestController는 해당 요청을 처리하고 데이터를 반환한다.
	 * 
	 * */
	
	
	/*클라입장에서의 순서 :
	 * 클라이언트요청->레스트풀컨트롤러가 주소에 들어온 {user_id}로  DB뒤져서->결과받아와서-> boolean을 json으로 반환 */
	
	//즉, Back에서 DB를 조작한것과, Front에서 들어온 요청이 여기서 만나서-> 결과를 json으로 반환하는것!
	
	
	
	
	@Autowired
	private UserService userService;
	
	//클라이언트-> 컨트롤러로 주소에 데이터를 직접적으로 붙여들어온 경우, @PathVariable을 매개변수에 붙여 받아야한다.
	//cf) 파라미터이름과 값을 세트로 구분자로 붙여 보낸경우에는 @RequestParam으로 받으면된다
	@GetMapping("/user/checkUserIdExist/{user_id}")
	public String checkUserIdExist(@PathVariable String user_id) {
		
		boolean chk=userService.checkUserIdExist(user_id);
		
		
		return chk+"";//오류막기위해 "" 하나 붙여준것
	}
	
}

//https://elfinlas.github.io/2018/02/18/spring-parameter/
