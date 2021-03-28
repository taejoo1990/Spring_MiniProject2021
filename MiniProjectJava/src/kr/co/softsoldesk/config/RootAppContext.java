package kr.co.softsoldesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.softsoldesk.beans.UserBean;


// 프로젝트 시 작업이 구현되는 beans와 dao, mapper등을 등록하는 곳
@Configuration
public class RootAppContext {
	
	
	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean() {
		
		return new UserBean();	
	}
	
	

}
