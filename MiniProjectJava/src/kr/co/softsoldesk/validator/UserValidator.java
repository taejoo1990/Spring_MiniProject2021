package kr.co.softsoldesk.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.co.softsoldesk.beans.UserBean;

public class UserValidator implements Validator{

	// ��ȿ�� �˻� �ش�
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.isAssignableFrom(clazz);
		// userBean�� ��ȿ�� �˻簡 Ÿ������ ����
		//  userBean >> target
	}

	@Override
	public void validate(Object target, Errors errors) {
		// ����ȯ(Ÿ�Ժ�ȯ)
		UserBean userBean = (UserBean)target; // ����ȯ �Ǿ ��� �� ���(�о�) �ȴ�. (joinUserBean, tempLoginUserBean � �о���̰� ��)
		
		
		String beanName = errors.getObjectName();
		//System.out.println(beanName); // tempLoginUserBean �о�鿩�� ������ ���Ƿ� ó���� ����� ��
		
		if (beanName.equals("joinUserBean") || beanName.equals("modifyUserBean")) { // �̷��� ó��!
			if (userBean.getUser_pw().equals(userBean.getUser_pw2()) == false) {
				errors.rejectValue("user_pw", "NotEquals");
				errors.rejectValue("user_pw2", "NotEquals"); // ����������Ƽ ���
			}
			
			// ȸ�����Խ� ���̵� �ߺ�üũ
			if (beanName.equals("joinUserBean")) {
				if (userBean.isUserIdExist() == false) {
					errors.rejectValue("user_id", "DontCheckUserIdExist");
				}
			}
		}
		
		
		
		
	}
	
	
	
	
	

}
