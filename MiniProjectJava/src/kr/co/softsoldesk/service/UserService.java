package kr.co.softsoldesk.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.dao.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	

	
	
	
	//���� DB���� �����°��� �����ִ°����� ���°����� �Ǵ��ؾ��Ѵ�.
	//sql�������� ���� ������� ���� �Ǵ��� ����������ϹǷ�, �����ڷ����� boolean
	
	public boolean checkUserIdExist(String user_id) {
		
		//DAO���� sql���� id�������� ������ ã�Ƴ� �̸������� ��� - user_name
		String user_name= userDAO.checkUserIdExist(user_id);
		
		
		if(user_name==null) {
			return true;// ��밡��
		}else {
			return false;// ���Ұ���
		}
		
		//�� �����, ��Ʈ�ѷ��ܿ��� ����ƮǮ��Ʈ�ѷ��� ajax���. jason�������.
		
	}
	

	public void addUserInfo(UserBean joinUserBean) {
		userDAO.addUserInfo(joinUserBean);
	}
	
	
	
	//��Ʈ���� ���ǹ߻��� �ö�� loginUserBean
	public void getLoginUserInfo(UserBean temploginUserBean) {
		

		//temp: ���� ���� �Է��� ���� ��Ƽ� ���ǿ��ٰ� �ӽ� �����س��� ��.
		//loginUserBean : ���̽��� root���� �������� ����(���� �������)--> ���� �Է������� �װ��� �����ϸ��
		//temp�� login������ �Ѱ��ָ��.(����->��ü)
		
		//���� : ��ܿ��� �Է¹��� ���� ��� DB�� ���� ã�ƿ� ��.
		UserBean temploginUserBean2= userDAO.getLoginUserInfo(temploginUserBean);
		
		//null�� �ƴ϶��(��,DB���� ã�ƿ� ����� �־��� ���)-> �󲮵��⿡ �����Ѵ�. 
		if(temploginUserBean2!=null) {
			 
			loginUserBean.setUser_idx(temploginUserBean2.getUser_idx());
			loginUserBean.setUser_name(temploginUserBean2.getUser_name());
			
			//����ε� ���Ǹ� ����� DB���� ã�ƿ°��� �� ����������, �α��λ��¸� �ʱⰪ�� false���� true�� �ٲ��ָ��.(�α��λ��·� �ٲ�)
			loginUserBean.setUserlogin(true);
			//���� �α��ε� �������� ��Ʈ�ѷ��ܿ��ٰ��� �˷��ָ� ��.
			
			System.out.println(loginUserBean.isUserlogin());
		}
	
		
		
		
	}
	
	//===================== MODIFY =========================================================
	
	//������, ��Ʈ�ѷ����� �޾ƿ� modifyUserBean �־���Ѵ�. �׷��� DAO�� ���� user_idx ���� �־��� �� �ִ�. 
	
	public void getModifyUserInfo(UserBean modifyUserBean) {
		
		//�α��ε� ����(loginUserBean)����, user_idx�� ����ͼ� getModifyUserInfo�޼��带 �����Ѵ�.(�����ϸ� DAO->Mapper->DB�� �ȴ�)
		//�� ������ ����� tempModifyUserBean�� �Ҵ��ߴ�. (���� �̰��� modifyUserBean�� �����ϸ�, ��Ʈ�ѷ��� �信�� Ȱ���� �� �ְ� �ȴ�)
		UserBean tempModifyUserBean=userDAO.getModifyUserInfo(loginUserBean.getUser_idx());
		
		//�Ű������� ���� �༮(�������ּ��� ��� ���� ����)���ٰ� DB�κ��� �ҷ��� ���� ����. 
		modifyUserBean.setUser_idx(tempModifyUserBean.getUser_idx());
		modifyUserBean.setUser_id(tempModifyUserBean.getUser_id());
		modifyUserBean.setUser_name(tempModifyUserBean.getUser_name());
		
		
	}
	
	//�̹� DB����� DAO���� ���԰�, Service������ �̰��� �����ܰ��� ��Ʈ�ѷ��� ��� ������ �� �ְ� �����ϸ�ȴ�.
	public void modifyUserInfo(UserBean modifyUserBean) {
		
		//DAO���� �غ� �غ�.(modifyUserBean�� idx����)
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
	
		//�غ��� ������ DAO������ ������Ʈ�� ��.
		userDAO.modifyUserInfo(modifyUserBean);
		
		
		
	}
	
	
}
