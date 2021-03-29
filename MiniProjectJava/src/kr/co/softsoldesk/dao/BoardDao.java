package kr.co.softsoldesk.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.mapper.BoardMapper;

@Repository
public class BoardDao {

	
	@Autowired
	private BoardMapper boardMapper;
	
	public void addContentInfo(ContentBean writeContentBean) {
		
		boardMapper.addContentInfo(writeContentBean);
	}
	
	public List<ContentBean> getContentList(int board_info_idx){
		return boardMapper.getContentList(board_info_idx);
	}
	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}
}
