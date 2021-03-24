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
	
	@Resource(name="LoginBean")
	private UserBean loginUserBean;
	
	
	//-------------------------------------------------------
	public boolean checkUserIDExist(String user_id) {
	String user_name = userDAO.checkUserIDExist(user_id);
	
	if(user_name==null) {
		return true; //사용가능
	}
	return false; //사용불가능
}
	
	
	//------------------------------------------------------
	public void addUserInfo(UserBean JoinUserBean) {
		userDAO.addUserInfo(JoinUserBean);
	}

	
	//--------------------------------------------------------------------
	public void getLoginUserInfo(UserBean tempLoginUserBean) {
		UserBean tempLoginUserBean2=userDAO.getLoginUserInfo(tempLoginUserBean);
		
		if(tempLoginUserBean2 !=null) {
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUser_login(true); //로그인 상태
		}
		
	}
}
