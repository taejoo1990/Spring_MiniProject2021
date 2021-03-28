package kr.co.softsoldesk.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.co.softsoldesk.beans.UserBean;

public class UserValidator implements Validator{

	// 유효성 검사 해당
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserBean.class.isAssignableFrom(clazz);
		// userBean에 유효성 검사가 타당한지 점검
		//  userBean >> target
	}

	@Override
	public void validate(Object target, Errors errors) {
		// 형변환(타입변환)
		UserBean userBean = (UserBean)target; // 형변환 되어서 모두 다 통과(읽어) 된다. (joinUserBean, tempLoginUserBean 등도 읽어들이게 됨)
		
		
		String beanName = errors.getObjectName();
		//System.out.println(beanName); // tempLoginUserBean 읽어들여서 에러가 나므로 처리를 해줘야 함
		
		if (beanName.equals("joinUserBean") || beanName.equals("modifyUserBean")) { // 이렇게 처리!
			if (userBean.getUser_pw().equals(userBean.getUser_pw2()) == false) {
				errors.rejectValue("user_pw", "NotEquals");
				errors.rejectValue("user_pw2", "NotEquals"); // 에러프로퍼티 등록
			}
			
			// 회원가입시 아이디 중복체크
			if (beanName.equals("joinUserBean")) {
				if (userBean.isUserIdExist() == false) {
					errors.rejectValue("user_id", "DontCheckUserIdExist");
				}
			}
		}
		
		
		
		
	}
	
	
	
	
	

}
