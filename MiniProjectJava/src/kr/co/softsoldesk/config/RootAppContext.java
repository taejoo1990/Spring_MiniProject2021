package kr.co.softsoldesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.softsoldesk.beans.UserBean;

//�������� �۾��� �����Ǵ� beans�� dao,mapper���� ����ϴ� ��
@Configuration
public class RootAppContext {

	//SessionScope : ������ �Ǵ¼���, �������� �ö���� ���� �α��ΰ��ú��� �����Ǹ鼭 �����̳ʿ� �ö���°�. ������ ������ �����ʰ� Bean���� ���������Ѵ�.
	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean() {
		
				
		return new UserBean();
	}
	
}
