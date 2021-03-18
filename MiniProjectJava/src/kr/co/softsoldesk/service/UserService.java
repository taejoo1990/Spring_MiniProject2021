package kr.co.softsoldesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softsoldesk.dao.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	public boolean checkUserIDExist(String user_id) {
	String user_name = userDAO.checkUserIDExist(user_id);
	
	if(user_name==null) {
		return true; //��밡��
	}
	return false; //���Ұ���
}
}
