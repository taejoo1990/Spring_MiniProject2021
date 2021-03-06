package kr.co.softsoldesk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import kr.co.softsoldesk.beans.ContentBean;

//MyBatis의 MapperInterface는 sql문을 갖고있고, RowMapper/ ResultSet 까지 세팅하여 반환한다
public interface BoardMapper {

//어느보드에 글을 쓸것인가까지 싹 정해서, 폼에서 받은내용이 들어가도록.
	//statement : "select content_seq.nextval from dual" : 현재 발생된 시퀀시번호를 주입(10->11)
	//keyProperty : writeContentBean 갖고있는 content_idx 담기
	//before = true : 아래의 쿼리문이 실행되기 전에 먼저 실행
	//resultType = ing.class : content_idx의 결과는 int로 받음
	//그러므로 BoardController write_pro에서 writeContentBean에는 새로 주입된 글번호가 들어감

	@SelectKey(statement = "select content_seq.nextval from dual", keyProperty = "content_idx", before = true, resultType = int.class)

	
	@Insert("insert into content_table(content_idx, content_subject, content_text, "
			+ "content_file,content_write_idx, content_board_idx,content_date) "
			+ "values (#{content_idx}, #{content_subject}, #{content_text}, #{content_file,jdbcType=VARCHAR}, "
			+ "#{content_write_idx}, #{content_board_idx}, sysdate)")
	void addContentInfo(ContentBean writeContentBean);
	
	@Select("select board_info_name " + 
			"from board_info_table " + 
			"where board_info_idx = #{board_info_idx}")
	String getBoardInfoName(int board_info_idx);

	@Select("select a1.content_idx, a1.content_subject, a2.user_name as content_write_name, " + 
			"       to_char(a1.content_date, 'YYYY-MM-DD') as content_date " + 
			"from content_table a1, user_table a2 " + 
			"where a1.content_write_idx = a2.user_idx " + 
			"      and a1.content_board_idx = #{board_info_idx} " + 
			"order by a1.content_idx desc")
	List<ContentBean> getContentList(int board_info_idx, RowBounds rowBounds);
	
	@Select("select a2.user_name as content_write_name, "
			+ "to_char(a1.content_date, 'yyyy-mm-dd') as content_date, "
			+ "a1.content_subject, a1.content_text, a1.content_file, a1.content_write_idx "
			+ "from content_table a1, user_table a2 "
			+ "where a1.content_write_idx=a2.user_idx and content_idx=#{content_idx}")
	ContentBean getContentInfo(int content_idx);
	
	@Update("update content_table " +
			"set content_subject = #{content_subject}, content_text = #{content_text}, " +
			"content_file = #{content_file, jdbcType=VARCHAR} " +
			"where content_idx = #{content_idx}")
	void modifyContentInfo(ContentBean modifyContentBean);
	
	@Delete("delete from content_table where content_idx = #{content_idx}")
	void deleteContentInfo(int content_idx);
	//페이징 처리
	@Select("select count(*) from content_table where content_board_idx = #{content_board_idx}")
	int getContentCnt(int content_board_idx);

}
