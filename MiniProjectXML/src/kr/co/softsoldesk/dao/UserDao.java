package kr.co.softsoldesk.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.beans.UserBean;

@Repository
public class UserDao {


	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public String checkUserIDExist (String user_id){
		return sqlSessionTemplate.selectOne("user.CheckUserIdExist", user_id);
		
	}
	
	public void addUserInfo (UserBean JoinUserBean) {	
		sqlSessionTemplate.insert("user.addUserInfo", JoinUserBean);
		
	}
}


	

