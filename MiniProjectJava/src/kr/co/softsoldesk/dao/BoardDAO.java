package kr.co.softsoldesk.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.mapper.BoardMapper;

@Repository
public class BoardDAO {

	@Autowired
	private BoardMapper boardMapper;
	
	public void addContentInfo(ContentBean writeContentBean) {
		//리턴값없으니 여기서 sql문 결과물 보면됨. 
		//for(int i=0;i<542;i++) {
		boardMapper.addContentInfo(writeContentBean);
		//}
	}
	public String getBoardInfoName(int board_info_idx) {
		return boardMapper.getBoardInfoName(board_info_idx);
	}

	public List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds){
		return boardMapper.getContentList(board_info_idx,rowBounds);
	}
	public ContentBean getContentInfo(int content_idx) {
		return boardMapper.getContentInfo(content_idx);
	}
	public void modifyContentInfo(ContentBean modifyContentBean) {
		boardMapper.modifyContentInfo(modifyContentBean);
	}
	public void deleteContentInfo(int content_idx) {
		boardMapper.deleteContentInfo(content_idx);
	}
	public int getContentCnt(int content_board_idx) {
		return boardMapper.getContentCnt(content_board_idx);
	}

	
}
