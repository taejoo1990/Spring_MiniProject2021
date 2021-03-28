package kr.co.softsoldesk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.mapper.UserMapper;

@Repository
public class UserDAO {

	@Autowired
	private UserMapper userMapper;

	
	public void addUserInfo(UserBean JoinUserBean) {
		userMapper.addUserInfo(JoinUserBean);
	}
	
	
	public String checkUserIDExist(String user_id) {
		return userMapper.checkUserIdExist(user_id);
	}
	
	
	public UserBean getLoginUserInfo(UserBean tempLoginUserBean) {
		return userMapper.getLoginUserInfo(tempLoginUserBean);
		
	}
	
}
