package kr.co.softsoldesk.beans;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class ContentBean {

	
	private int content_idx;//게시글번호
	
	@NotBlank
	private String content_subject;
	
	@NotBlank
	private String content_text;
	
	
	//정적데이터 타입 : enctype="multipart/form-data"
	//브라우저에서 인식하는 실제 파일데이터.---> write.jsp에서 첨부이미지에서는 이녀석을 잡아야한다.
	private MultipartFile upload_file;
	
	//우리가 보는 String형태의 파일 이름. 
	private String content_file;//DB에 보낼 파일이름.
	
	private int content_write_idx;//글쓴 사람contetn_write_idx
	
	private int content_board_idx;//글쓴 게시판 번호.어느 게시판에 쓴것인가.
	
	private String content_date;
	
	private String content_write_name;
	
	
	
	
	public String getContent_text() {
		return content_text;
	}

	public void setContent_text(String content_text) {
		this.content_text = content_text;
	}
	
	
	

	public int getContent_idx() {
		return content_idx;
	}

	public void setContent_idx(int content_idx) {
		this.content_idx = content_idx;
	}

	public String getContent_subject() {
		return content_subject;
	}

	public void setContent_subject(String content_subject) {
		this.content_subject = content_subject;
	}

	
	public MultipartFile getUpload_file() {
		return upload_file;
	}

	public void setUpload_file(MultipartFile upload_file) {
		this.upload_file = upload_file;
	}
	
	

	public String getContent_file() {
		return content_file;
	}

	public void setContent_file(String content_file) {
		this.content_file = content_file;
	}

	public int getContent_write_idx() {
		return content_write_idx;
	}

	public void setContent_write_idx(int content_write_idx) {
		this.content_write_idx = content_write_idx;
	}

	public int getContent_board_idx() {
		return content_board_idx;
	}

	public void setContent_board_idx(int content_board_idx) {
		this.content_board_idx = content_board_idx;
	}

	public String getContent_date() {
		return content_date;
	}

	public void setContent_date(String content_date) {
		this.content_date = content_date;
	}

	public String getContent_write_name() {
		return content_write_name;
	}

	public void setContent_write_name(String content_write_name) {
		this.content_write_name = content_write_name;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
