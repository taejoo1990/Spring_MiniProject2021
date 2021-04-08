package kr.co.softsoldesk.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.beans.PageBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.dao.BoardDAO;

//프로퍼티즈에 만들어놓은 정적데이터 업로드경로인지시키기. @PropertySource
@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {

	// option.properties 에 있는, path.upload 뒤의 경로값을 가져와서 path_upload에 저장한다.
	@Value("${path.upload}")
	private String path_upload;

	// DAO 자동주입
	@Autowired
	private BoardDAO boardDAO;
//-------------페이징--------------------------
	@Value("${page.listcnt}")
	private int page_listcnt;

	@Value("${page.paginationcnt}")
	private int page_paginationcnt;
//-------------------------------------------

	// 로그인 세션정보도 잡아와야함.
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	// =================================================
	// https://gunbin91.github.io/spring/2019/09/24/spring_12_multipart.html

	// ContentBean에있는, 실제서버가 다루는 Raw파일정보가 매개변수로 들어온다.(밑에 addContentInfo 메서드가 가져올것)

	// 받아온 객체를 업로드 처리하지 않으면 임시파일에 저장된 파일이 자동적으로 삭제되기 때문에,
	// MultipartFile객체의 transferTo(File f) 메서드를 이용해서 업로드처리를 해야 한다.

	private String saveUploadFile(MultipartFile upload_file) {

		// 중복되는 파일명이 있을 수 있기때문에, 밀리초단위의 현재시간과 Raw파일이 제공하는 파일네임(getName으로 알수있음)을 합쳐서,
		// 중복되지않도록한다.
		// getOriginalname을 하면, 업로드된 파일이 원래 위치하고 있던 곳의 주소가 붙어온다.
		String file_name = System.currentTimeMillis() + "_" + upload_file.getName();

		// =====cf) 52번의 경로 오류시 솔루션
		/*
		 * String file_name = System.currentTimeMillis() + "_" +
		 * FilenameUtils.getBaseName(upload_file.getOriginalFilename()) + "." +
		 * FilenameUtils.getExtension(upload_file.getOriginalFilename());
		 */

		try {
			// write.jsp에서 인풋타입이 file이기때문에, file타입으로 Raw데이터가 변환되어야한다.
			// 괄호안 : file객체를 만드는데, 뒤에 온 String을 경로로 잡는 file을 만든것.
			upload_file.transferTo(new File(path_upload + "/" + file_name));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// try문안에서 임시파일을 업로드처리해놓고, 시간을 붙여 ReName한 주소를 리턴한다.
		return file_name;

	}
	// ================ 컨텐츠 추가 =====================================

	public void addContentInfo(ContentBean writeContentBean) {

		/*
		 * 데이터 잘 넘어오는지 확인 System.out.println(writeContentBean.getContent_subject());
		 * //게시글 이름 System.out.println(writeContentBean.getContent_text()); //게시글 내용
		 * System.out.println(writeContentBean.getUpload_file().getSize()); //첨부이미지 사이즈
		 */

		MultipartFile upload_file = writeContentBean.getUpload_file(); // Raw File의 정보를 ContentBean에 가서 물어온다.

		// 첨부파일이 있으면(0아니면) 저장해라.
		if (upload_file.getSize() > 0) {

			// 업로드한 Rawfile길이가 0 이 아니면, 위의 saveUploadFile메서드 돌려서, 중복을 제거한 file_name에 저장한다.
			String file_name = saveUploadFile(upload_file);
			System.out.println(file_name);

			// 첨부파일 호출. DB에 집어넣을때, 위에서 가공한 String 파일 이름을 세팅해서 집어넣음.
			writeContentBean.setContent_file(file_name);

		}
		// 이제 저장할건데, 세션으로부터 로그인정보를 가져와서, 누가 썼는지 인덱스번호(가입할때마다 시퀀스번호생성되는것) 가져와서 세팅한다.
		// 어느 유저가 글인지를 확인해서, 맞으면 수정/삭제시키려한다. 계속 끌고다녀야함.
		writeContentBean.setContent_write_idx(loginUserBean.getUser_idx());
		boardDAO.addContentInfo(writeContentBean);

	}

	public String getBoardInfoName(int board_info_idx) {
		return boardDAO.getBoardInfoName(board_info_idx);
	}
//------------------------------페이징----------------------------
	public List<ContentBean> getContentList(int board_info_idx, int page){
		//RowBounds : (start, end)
		/* page 1 - 1 -> 0 + 9 = 9
		 * pag2 2 - 1 -> 10 + 9 = 19  
		 * page 3 -1 -> 20 +9 = 29*/
		int start = (page -1) * page_listcnt;
		RowBounds rowBounds = new RowBounds(start, page_listcnt);
		
		return boardDAO.getContentList(board_info_idx, rowBounds);
	}
	//----------------카운터링빈
	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		//전체글의 갯수
		int content_cnt = boardDAO.getContentCnt(content_board_idx);
		
		PageBean pageBean = new PageBean(content_cnt, currentPage, page_listcnt, page_paginationcnt);
		
		return pageBean;
	}


//----------------------------------------------------------------
	public ContentBean getContentInfo(int content_idx) {
		return boardDAO.getContentInfo(content_idx);
	}
	public void modifyContentInfo(ContentBean modifyContentBean) {

		// 첨부파일에 대한 처리
		MultipartFile upload_file = modifyContentBean.getUpload_file();

		if (upload_file.getSize() > 0) { // upload가 있다면
			String file_name = saveUploadFile(upload_file);
			modifyContentBean.setContent_file(file_name);
		}
		boardDAO.modifyContentInfo(modifyContentBean);
	}
	public void deleteContentInfo(int content_idx) {
		boardDAO.deleteContentInfo(content_idx);
	}

}

// ==============================================================================
