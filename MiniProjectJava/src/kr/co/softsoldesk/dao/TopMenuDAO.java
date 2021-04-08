package kr.co.softsoldesk.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.mapper.TopMenuMapper;

@Repository
public class TopMenuDAO {

	
	//DAO���� sql "�޼���"�� ���� Repository�� �ٰ�, �Ϲݸ޼��忡�� Service�� �ٴ´�.
	//sql ������� �޴� �޼������ ���⿡ �δ°�.
	// sql "��������ü"�� Mapper Interface�� �д�. 
	@Autowired
	private TopMenuMapper topMenuMapper;
	
	public List<BoardInfoBean> getTopMenuList(){
		
		List<BoardInfoBean> topMenuList=topMenuMapper.getTopMenuList();
	
	
		return topMenuList;
	}
	
	
}
