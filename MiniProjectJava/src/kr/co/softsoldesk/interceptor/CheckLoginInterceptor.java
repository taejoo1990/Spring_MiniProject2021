package kr.co.softsoldesk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.softsoldesk.beans.UserBean;

public class CheckLoginInterceptor implements HandlerInterceptor {

	//�α��ο��� �Ǵ� autowired�� �Ұ��ϹǷ� �����ڸ� �b��
	private UserBean loginUserBean;
	
	public CheckLoginInterceptor(UserBean LoginUserBean) {
		this.loginUserBean=LoginUserBean;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//�α����� �Ǿ� ���� ������
		if(loginUserBean.isUser_login()==false) {
			//�α��� �Ǿ����� ���� �����̹Ƿ�, �α����� ��θ� ����
			String contextPath=request.getContextPath();
			//�α��� ó���� �� �Ǿ� �����Ƿ�, �α���ȭ������ �����̷�Ʈ
			response.sendRedirect(contextPath + "/user/not_login");
			//�����ܰ�� �̵����� ����
			return false; //�α��� ��
		}
		return true; //�α��� ��
	}


	
	
}
