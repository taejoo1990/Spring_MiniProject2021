package kr.co.softsoldesk.validator;

import org.springframework.validation.Errors;

import kr.co.softsoldesk.beans.UserBean;

public class UserValidator implements org.springframework.validation.Validator {

	//-----------사용자정의 에러코드 / 사용자정의 에러어노테이션(사용자정의 에러어노테이션등록할때는 컨트롤러에서 InitBinder)------------
	//비번이 일치하는지 확인하는 validator : springframework의 validator 구현
		//과연, 어노테이션이 올라가서 유효성 검사를 할 수 있는 빈인가?
		
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return UserBean.class.isAssignableFrom(clazz); //유저빈이 유효성검사가능한 빈인가?(1차검사)
	}

	
	//--------에러체크가능함이 검증되어있음----------------------------------------------------------
	
	
	//Object타입인데, 타겟은 위에서 통과된 UserBean. 구현부에서 형변환해줘야한다
	@Override
	public void validate(Object target, Errors errors) {
		
		//타겟 형변환-타겟이 UserBean이므로, 싹 걸린다.
		//여기에서 UserBean userBean2 등으로 처리하는 방법도 있지만, 번거로우므로 다르게 처리하자. 
		//if로 joinUserBean인지를 확인해서 처리하면된다.
		
		UserBean userBean=(UserBean)target;
		
		//여기에 어떤 녀석들이 걸리고있는지 확인해보자(DonCheck오류관련)---->걸린 이름들이 무엇인지 확인한 다음, joinUserBean일 경우에만 걸리도록 조건문 처리
		String beanName=errors.getObjectName();
		System.out.println(beanName);
		
		//결국, 매개변수에 (UserBean userBean, Errors errors)가 있는것과 동일해졌다.
		//여기에서의 errors는, 유저컨트롤러에서의 Binding Result와 같은 역할이다.
		
		//비밀번호가 pw와 pw2가 같은지 확인해야함
		
		//둘이 서로 달라서 false 발생시, NotEquals라는 사용자정의 에러 발생---> 에러프로퍼티에 설정하면됨
		
		
		//============여기에 어떤 녀석들이 걸리고있는지 확인해보자(DonCheck오류관련)---->걸린 이름들이 무엇인지 확인한 다음, joinUserBean일 경우에만 걸리도록 
		//=======modifyUserBean 에서 NotEquals가 발생하더라도 걸릴수 있도록 설정. 
		
		if(beanName.equals("joinUserBean")||beanName.equals("modifyUserBean")) {
		
		if(userBean.getUser_pw().equals(userBean.getUser_pw2())==false) {
			errors.rejectValue("user_pw", "NotEquals");
			errors.rejectValue("user_pw2", "NotEquals");
			
		}
		
		if(beanName.equals("joinUserBean")) {
		
		//user_id에 에러가 있을경우 발생시킬 사용자정의 에러 DontCheckUserIdExist(중복확인을 안한경우 발생시킬에러)
		//##### 로그인 시, userBean에 걸린 모든 Validator를 다 읽기때문에, join시에만 쓰려던 이 에러가 문제가 생긴다. 정리해줘야함! #####
		if(userBean.isUserIdExist()==false) {
			errors.rejectValue("user_id", "DontCheckUserIdExist");
		  }
		}
		
			
	}//회원가입관련 오류 끝
		
		
		
	

	
	}
	
	
	
}
