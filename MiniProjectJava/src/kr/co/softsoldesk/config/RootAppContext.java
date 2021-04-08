package kr.co.softsoldesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.softsoldesk.beans.UserBean;

//프로젝시 작업시 구현되는 beans와 dao,mapper등을 등록하는 곳
@Configuration
public class RootAppContext {

	//SessionScope : 컴파일 되는순간, 브라우저가 올라오는 순간 로그인관련빈이 생성되면서 컨테이너에 올라오는것. 떄문에 서블렛에 두지않고 Bean으로 별도설정한다.
	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean() {
		
				
		return new UserBean();
	}
	
}
