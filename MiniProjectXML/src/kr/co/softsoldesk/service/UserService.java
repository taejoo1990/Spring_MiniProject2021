package kr.co.softsoldesk.service;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.softsoldesk.dao.UserDao;

public class UserService {

	@Autowired
	private UserDao userDAO;
	
	public boolean checkUserIDExist(String user_id) {
	String user_name = userDAO.checkUserIDExist(user_id);
	
	if(user_name==null) {
		return true; //��밡��
	}
	return false; //���Ұ���
}
	
}
