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
			return true; // 아이디 중복이 없으므로 사용가능
		}else {
			return false; // 아이디 중복이므로 사용불가능
		}
	}
	
	public void addUserInfo(UserBean joinUserBean) {
		userDao.addUserInfo(joinUserBean);
	}
	
	
	public void getLoginUserInfo(UserBean tempUserBean) {
		UserBean tempLoginUserBean2 = userDao.getLoginUserInfo(tempUserBean); // 결과 값이 tempLoginUserBean2 담김
		
		// 가져온 데이터가 있다면
		if(tempLoginUserBean2 != null) {
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUserlogin(true); // 로그인 상태 
		}
	}
	
	// 정보수정 페이지 이름, 아이디 가져오기
	public void getModifyUserInfo(UserBean modifyUserInfo) {
		UserBean tempModifyUserBean = userDao.getModifyUserInfo(loginUserBean.getUser_idx());
		// root 로 잡아놔서 여러번 불러올 수 있음
		// loginUserBean : 로그인 된 유저의 정보
		
		modifyUserInfo.setUser_idx(tempModifyUserBean.getUser_idx());
		modifyUserInfo.setUser_name(tempModifyUserBean.getUser_name());
		modifyUserInfo.setUser_id(tempModifyUserBean.getUser_id());        
		// modifyUserInfo >> modify.jsp에 연결
	}
	
	// 정보수정 페이지 정보수정 
	public void modifyUserInfo(UserBean modifyUserInfo) {
		
		modifyUserInfo.setUser_idx(loginUserBean.getUser_idx());
		
		userDao.modifyUserInfo(modifyUserInfo);
		
	}
	
	
	
	
	
}
