package kr.co.softsoldesk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.mapper.UserMapper;

@Repository
public class UserDAO {

	// MapperFactoryBean으로 usermapper 등록시켜놨으므로 바로 오토와이어드로 땡길수있다.
	@Autowired
	private UserMapper userMapper;

	// Mapper인터페이스의 메서드 호출. preparedstatement(mapper)를 돌린 결과물을 여기에 두고 서비스로 넘긴다

	public String checkUserIdExist(String user_id) {

		return userMapper.checkUserIdExist(user_id);

	}

	public void addUserInfo(UserBean joinUserBean) {
		userMapper.addUserInfo(joinUserBean);
	}

	public UserBean getLoginUserInfo(UserBean temploginUserBean) {
		return userMapper.getLoginUserInfo(temploginUserBean);
	}

	
	//----- 수정
	//idx를 기준으로 맵퍼가 작동되므로, 로그인된 상태의 빈에 세팅되어있는 idx를 가지러 서비스로 가자.
	public UserBean getModifyUserInfo(int user_idx) {
		return userMapper.getModifyUserInfo(user_idx);
	}

	
	//Mapper한테 일만 시키면 끝. 맵퍼에 설정한 쿼리문을 execute
	public void modifyUserInfo(UserBean modifyUserBean) {
		
		userMapper.modifyUserInfo(modifyUserBean);
	}
	
	
}
