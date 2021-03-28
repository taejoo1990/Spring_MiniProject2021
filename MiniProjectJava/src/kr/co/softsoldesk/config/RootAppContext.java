package kr.co.softsoldesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.softsoldesk.beans.UserBean;


// ������Ʈ �� �۾��� �����Ǵ� beans�� dao, mapper���� ����ϴ� ��
@Configuration
public class RootAppContext {
	
	
	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean() {
		
		return new UserBean();	
	}
	
	

}
