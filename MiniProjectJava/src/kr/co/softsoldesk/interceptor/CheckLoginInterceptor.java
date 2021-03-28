package kr.co.softsoldesk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.UserBean;

public class CheckLoginInterceptor implements HandlerInterceptor {

	
	// �α��� ���θ� �Ǵ��ؾ� �ϹǷ� loginUserBean��ü ����
	private UserBean loginUserBean;
	
	//AutoWire�� �ȵǹǷ� �����ڸ� Ȱ���Ѵ� .. ���ͼ��ʹ� ������̾ �ȵ�
	public CheckLoginInterceptor(UserBean loginUserBean) {
		this.loginUserBean = loginUserBean;
	}
	
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// �α����� �Ǿ� ���� ������
		if(loginUserBean.isUserlogin() == false) {
			// �α����� �Ǿ� ���� ���� �����̹Ƿ� �α��� �� ��θ� ����
			String contextPath = request.getContextPath();
			
			// �α���ó���� �ȵǾ� �����Ƿ� not_login���� ������ ��ȯ
			response.sendRedirect(contextPath + "/user/not_login"); // �α��� �ȵǸ� �Ϸ� ��
			
			// �����ܰ�� �̵����� ����
			return false; // �α��� ��
			
		}
		
		return true; // �α��� ��
	}
	
	
	
	
	
	
	
	
	

}
