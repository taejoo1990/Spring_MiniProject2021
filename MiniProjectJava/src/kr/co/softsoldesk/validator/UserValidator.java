package kr.co.softsoldesk.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.co.softsoldesk.beans.UserBean;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// ����ȯ
		UserBean userBean = (UserBean) target;

		String beanName = errors.getObjectName();
		//Bean�� �Ű��� ����ϴ� ��� ������ ��ȿ���˻縦 ���� �ʵ���, beanname�� Ư���Ͽ� �ִ�
		//����IF���� ����Ѵ�.
		if (beanName.equals("JoinUserBean")) {

			if (userBean.getUser_pw().equals(userBean.getUser_pw2()) == false) {
				errors.rejectValue("user_pw", "NotEquals");
				errors.rejectValue("user_pw2", "NotEquals");
			}

			if (userBean.isUserIdExist() == false) {
				errors.rejectValue("user_id", "DontCheckUserIdExist");
			}
		}
	}

}
