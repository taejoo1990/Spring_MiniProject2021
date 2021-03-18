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
		return true; //사용가능
	}
	return false; //사용불가능
}
}
