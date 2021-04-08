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
	
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	

	
	
	
	//이제 DB에서 가져온것이 쓸수있는것인지 없는것인지 판단해야한다.
	//sql쿼리문을 돌린 결과물에 대한 판단을 리턴해줘야하므로, 리턴자료형은 boolean
	
	public boolean checkUserIdExist(String user_id) {
		
		//DAO에서 sql문을 id기준으로 돌려서 찾아낸 이름에대한 결과 - user_name
		String user_name= userDAO.checkUserIdExist(user_id);
		
		
		if(user_name==null) {
			return true;// 사용가능
		}else {
			return false;// 사용불가능
		}
		
		//이 결과는, 컨트롤러단에서 레스트풀컨트롤러로 ajax통신. jason방식으로.
		
	}
	

	public void addUserInfo(UserBean joinUserBean) {
		userDAO.addUserInfo(joinUserBean);
	}
	
	
	
	//루트에서 세션발생시 올라온 loginUserBean
	public void getLoginUserInfo(UserBean temploginUserBean) {
		

		//temp: 내가 현재 입력한 값을 잡아서 세션에다가 임시 저장해놓은 곳.
		//loginUserBean : 케이스만 root에서 만들어놓은 상태(안은 비어있음)--> 뭔가 입력했으면 그것을 세팅하면됨
		//temp를 login쪽으로 넘겨주면됨.(세션->객체)
		
		//우항 : 뷰단에서 입력받은 것을 들고 DB에 가서 찾아온 것.
		UserBean temploginUserBean2= userDAO.getLoginUserInfo(temploginUserBean);
		
		//null이 아니라면(즉,DB에서 찾아온 대상이 있었을 경우)-> 빈껍데기에 세팅한다. 
		if(temploginUserBean2!=null) {
			 
			loginUserBean.setUser_idx(temploginUserBean2.getUser_idx());
			loginUserBean.setUser_name(temploginUserBean2.getUser_name());
			
			//제대로된 질의를 들고가서 DB에서 찾아온것을 싹 세팅했으면, 로그인상태를 초기값인 false에서 true로 바꿔주면됨.(로그인상태로 바꿈)
			loginUserBean.setUserlogin(true);
			//이제 로그인된 상태임을 컨트롤러단에다가도 알려주면 됨.
			
			System.out.println(loginUserBean.isUserlogin());
		}
	
		
		
		
	}
	
	//===================== MODIFY =========================================================
	
	//수정시, 컨트롤러에서 받아온 modifyUserBean 있어야한다. 그래야 DAO에 가서 user_idx 값을 넣어줄 수 있다. 
	
	public void getModifyUserInfo(UserBean modifyUserBean) {
		
		//로그인된 정보(loginUserBean)에서, user_idx를 갖고와서 getModifyUserInfo메서드를 실행한다.(실행하면 DAO->Mapper->DB가 된다)
		//싹 실행한 결과를 tempModifyUserBean에 할당했다. (이제 이것을 modifyUserBean에 설정하면, 컨트롤러나 뷰에서 활용할 수 있게 된다)
		UserBean tempModifyUserBean=userDAO.getModifyUserInfo(loginUserBean.getUser_idx());
		
		//매개변수로 들어온 녀석(세팅해주세요 라고 받은 빈껍질)에다가 DB로부터 불러온 내용 세팅. 
		modifyUserBean.setUser_idx(tempModifyUserBean.getUser_idx());
		modifyUserBean.setUser_id(tempModifyUserBean.getUser_id());
		modifyUserBean.setUser_name(tempModifyUserBean.getUser_name());
		
		
	}
	
	//이미 DB결과는 DAO에서 나왔고, Service에서는 이것을 다음단계인 컨트롤러가 뷰와 연결할 수 있게 세팅하면된다.
	public void modifyUserInfo(UserBean modifyUserBean) {
		
		//DAO돌릴 준비물 준비.(modifyUserBean에 idx세팅)
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
	
		//준비물을 가지고 DAO돌리면 업데이트는 끝.
		userDAO.modifyUserInfo(modifyUserBean);
		
		
		
	}
	
	
}
