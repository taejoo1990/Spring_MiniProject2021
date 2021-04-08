package kr.co.softsoldesk.validator;

import org.springframework.validation.Errors;

import kr.co.softsoldesk.beans.UserBean;

public class UserValidator implements org.springframework.validation.Validator {

	//-----------��������� �����ڵ� / ��������� ����������̼�(��������� ����������̼ǵ���Ҷ��� ��Ʈ�ѷ����� InitBinder)------------
	//����� ��ġ�ϴ��� Ȯ���ϴ� validator : springframework�� validator ����
		//����, ������̼��� �ö󰡼� ��ȿ�� �˻縦 �� �� �ִ� ���ΰ�?
		
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return UserBean.class.isAssignableFrom(clazz); //�������� ��ȿ���˻簡���� ���ΰ�?(1���˻�)
	}

	
	//--------����üũ�������� �����Ǿ�����----------------------------------------------------------
	
	
	//ObjectŸ���ε�, Ÿ���� ������ ����� UserBean. �����ο��� ����ȯ������Ѵ�
	@Override
	public void validate(Object target, Errors errors) {
		
		//Ÿ�� ����ȯ-Ÿ���� UserBean�̹Ƿ�, �� �ɸ���.
		//���⿡�� UserBean userBean2 ������ ó���ϴ� ����� ������, ���ŷο�Ƿ� �ٸ��� ó������. 
		//if�� joinUserBean������ Ȯ���ؼ� ó���ϸ�ȴ�.
		
		UserBean userBean=(UserBean)target;
		
		//���⿡ � �༮���� �ɸ����ִ��� Ȯ���غ���(DonCheck��������)---->�ɸ� �̸����� �������� Ȯ���� ����, joinUserBean�� ��쿡�� �ɸ����� ���ǹ� ó��
		String beanName=errors.getObjectName();
		System.out.println(beanName);
		
		//�ᱹ, �Ű������� (UserBean userBean, Errors errors)�� �ִ°Ͱ� ����������.
		//���⿡���� errors��, ������Ʈ�ѷ������� Binding Result�� ���� �����̴�.
		
		//��й�ȣ�� pw�� pw2�� ������ Ȯ���ؾ���
		
		//���� ���� �޶� false �߻���, NotEquals��� ��������� ���� �߻�---> ����������Ƽ�� �����ϸ��
		
		
		//============���⿡ � �༮���� �ɸ����ִ��� Ȯ���غ���(DonCheck��������)---->�ɸ� �̸����� �������� Ȯ���� ����, joinUserBean�� ��쿡�� �ɸ����� 
		//=======modifyUserBean ���� NotEquals�� �߻��ϴ��� �ɸ��� �ֵ��� ����. 
		
		if(beanName.equals("joinUserBean")||beanName.equals("modifyUserBean")) {
		
		if(userBean.getUser_pw().equals(userBean.getUser_pw2())==false) {
			errors.rejectValue("user_pw", "NotEquals");
			errors.rejectValue("user_pw2", "NotEquals");
			
		}
		
		if(beanName.equals("joinUserBean")) {
		
		//user_id�� ������ ������� �߻���ų ��������� ���� DontCheckUserIdExist(�ߺ�Ȯ���� ���Ѱ�� �߻���ų����)
		//##### �α��� ��, userBean�� �ɸ� ��� Validator�� �� �б⶧����, join�ÿ��� ������ �� ������ ������ �����. �����������! #####
		if(userBean.isUserIdExist()==false) {
			errors.rejectValue("user_id", "DontCheckUserIdExist");
		  }
		}
		
			
	}//ȸ�����԰��� ���� ��
		
		
		
	

	
	}
	
	
	
}
