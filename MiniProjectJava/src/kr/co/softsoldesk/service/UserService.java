package kr.co.softsoldesk.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	public boolean checkUserIdExist(String user_id) {
		
		String user_name = userDao.checkUserIdExist(user_id);
		
		if(user_name==null) {
			return true; // ���̵� �ߺ��� �����Ƿ� ��밡��
		}else {
			return false; // ���̵� �ߺ��̹Ƿ� ���Ұ���
		}
	}
	
	public void addUserInfo(UserBean joinUserBean) {
		userDao.addUserInfo(joinUserBean);
	}
	
	
	public void getLoginUserInfo(UserBean tempUserBean) {
		UserBean tempLoginUserBean2 = userDao.getLoginUserInfo(tempUserBean); // ��� ���� tempLoginUserBean2 ���
		
		// ������ �����Ͱ� �ִٸ�
		if(tempLoginUserBean2 != null) {
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUserlogin(true); // �α��� ���� 
		}
	}
	
	// �������� ������ �̸�, ���̵� ��������
	public void getModifyUserInfo(UserBean modifyUserInfo) {
		UserBean tempModifyUserBean = userDao.getModifyUserInfo(loginUserBean.getUser_idx());
		// root �� ��Ƴ��� ������ �ҷ��� �� ����
		// loginUserBean : �α��� �� ������ ����
		
		modifyUserInfo.setUser_idx(tempModifyUserBean.getUser_idx());
		modifyUserInfo.setUser_name(tempModifyUserBean.getUser_name());
		modifyUserInfo.setUser_id(tempModifyUserBean.getUser_id());        
		// modifyUserInfo >> modify.jsp�� ����
	}
	
	// �������� ������ �������� 
	public void modifyUserInfo(UserBean modifyUserInfo) {
		
		modifyUserInfo.setUser_idx(loginUserBean.getUser_idx());
		
		userDao.modifyUserInfo(modifyUserInfo);
		
	}
	
	
	
	
	
}
