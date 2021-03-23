package kr.co.softsoldesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.softsoldesk.beans.UserBean;


@Configuration
public class RootAppContext {

	@Bean(name = "LoginBean")
	@SessionScope
	public UserBean LoginUserBean(){
		UserBean bean = new UserBean();
		return bean;
	}
}
