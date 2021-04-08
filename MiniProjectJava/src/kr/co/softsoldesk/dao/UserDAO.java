package kr.co.softsoldesk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.mapper.UserMapper;

@Repository
public class UserDAO {

	// MapperFactoryBean���� usermapper ��Ͻ��ѳ����Ƿ� �ٷ� ������̾��� ������ִ�.
	@Autowired
	private UserMapper userMapper;

	// Mapper�������̽��� �޼��� ȣ��. preparedstatement(mapper)�� ���� ������� ���⿡ �ΰ� ���񽺷� �ѱ��

	public String checkUserIdExist(String user_id) {

		return userMapper.checkUserIdExist(user_id);

	}

	public void addUserInfo(UserBean joinUserBean) {
		userMapper.addUserInfo(joinUserBean);
	}

	public UserBean getLoginUserInfo(UserBean temploginUserBean) {
		return userMapper.getLoginUserInfo(temploginUserBean);
	}

	
	//----- ����
	//idx�� �������� ���۰� �۵��ǹǷ�, �α��ε� ������ �� ���õǾ��ִ� idx�� ������ ���񽺷� ����.
	public UserBean getModifyUserInfo(int user_idx) {
		return userMapper.getModifyUserInfo(user_idx);
	}

	
	//Mapper���� �ϸ� ��Ű�� ��. ���ۿ� ������ �������� execute
	public void modifyUserInfo(UserBean modifyUserBean) {
		
		userMapper.modifyUserInfo(modifyUserBean);
	}
	
	
}
