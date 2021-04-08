package kr.co.softsoldesk.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.mapper.TopMenuMapper;

@Repository
public class TopMenuDAO {

	
	//DAO에는 sql "메서드"만 오고 Repository가 붙고, 일반메서드에는 Service가 붙는다.
	//sql 결과물을 받는 메서드등을 여기에 두는것.
	// sql "쿼리문자체"는 Mapper Interface에 둔다. 
	@Autowired
	private TopMenuMapper topMenuMapper;
	
	public List<BoardInfoBean> getTopMenuList(){
		
		List<BoardInfoBean> topMenuList=topMenuMapper.getTopMenuList();
	
	
		return topMenuList;
	}
	
	
}
