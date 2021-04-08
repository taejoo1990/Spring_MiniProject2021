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

//������Ƽ� �������� ���������� ���ε���������Ű��. @PropertySource
@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService {

	// option.properties �� �ִ�, path.upload ���� ��ΰ��� �����ͼ� path_upload�� �����Ѵ�.
	@Value("${path.upload}")
	private String path_upload;

	// DAO �ڵ�����
	@Autowired
	private BoardDAO boardDAO;
//-------------����¡--------------------------
	@Value("${page.listcnt}")
	private int page_listcnt;

	@Value("${page.paginationcnt}")
	private int page_paginationcnt;
//-------------------------------------------

	// �α��� ���������� ��ƿ;���.
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	// =================================================
	// https://gunbin91.github.io/spring/2019/09/24/spring_12_multipart.html

	// ContentBean���ִ�, ���������� �ٷ�� Raw���������� �Ű������� ���´�.(�ؿ� addContentInfo �޼��尡 �����ð�)

	// �޾ƿ� ��ü�� ���ε� ó������ ������ �ӽ����Ͽ� ����� ������ �ڵ������� �����Ǳ� ������,
	// MultipartFile��ü�� transferTo(File f) �޼��带 �̿��ؼ� ���ε�ó���� �ؾ� �Ѵ�.

	private String saveUploadFile(MultipartFile upload_file) {

		// �ߺ��Ǵ� ���ϸ��� ���� �� �ֱ⶧����, �и��ʴ����� ����ð��� Raw������ �����ϴ� ���ϳ���(getName���� �˼�����)�� ���ļ�,
		// �ߺ������ʵ����Ѵ�.
		// getOriginalname�� �ϸ�, ���ε�� ������ ���� ��ġ�ϰ� �ִ� ���� �ּҰ� �پ�´�.
		String file_name = System.currentTimeMillis() + "_" + upload_file.getName();

		// =====cf) 52���� ��� ������ �ַ��
		/*
		 * String file_name = System.currentTimeMillis() + "_" +
		 * FilenameUtils.getBaseName(upload_file.getOriginalFilename()) + "." +
		 * FilenameUtils.getExtension(upload_file.getOriginalFilename());
		 */

		try {
			// write.jsp���� ��ǲŸ���� file�̱⶧����, fileŸ������ Raw�����Ͱ� ��ȯ�Ǿ���Ѵ�.
			// ��ȣ�� : file��ü�� ����µ�, �ڿ� �� String�� ��η� ��� file�� �����.
			upload_file.transferTo(new File(path_upload + "/" + file_name));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// try���ȿ��� �ӽ������� ���ε�ó���س���, �ð��� �ٿ� ReName�� �ּҸ� �����Ѵ�.
		return file_name;

	}
	// ================ ������ �߰� =====================================

	public void addContentInfo(ContentBean writeContentBean) {

		/*
		 * ������ �� �Ѿ������ Ȯ�� System.out.println(writeContentBean.getContent_subject());
		 * //�Խñ� �̸� System.out.println(writeContentBean.getContent_text()); //�Խñ� ����
		 * System.out.println(writeContentBean.getUpload_file().getSize()); //÷���̹��� ������
		 */

		MultipartFile upload_file = writeContentBean.getUpload_file(); // Raw File�� ������ ContentBean�� ���� ����´�.

		// ÷�������� ������(0�ƴϸ�) �����ض�.
		if (upload_file.getSize() > 0) {

			// ���ε��� Rawfile���̰� 0 �� �ƴϸ�, ���� saveUploadFile�޼��� ������, �ߺ��� ������ file_name�� �����Ѵ�.
			String file_name = saveUploadFile(upload_file);
			System.out.println(file_name);

			// ÷������ ȣ��. DB�� ���������, ������ ������ String ���� �̸��� �����ؼ� �������.
			writeContentBean.setContent_file(file_name);

		}
		// ���� �����Ұǵ�, �������κ��� �α��������� �����ͼ�, ���� ����� �ε�����ȣ(�����Ҷ����� ��������ȣ�����Ǵ°�) �����ͼ� �����Ѵ�.
		// ��� ������ �������� Ȯ���ؼ�, ������ ����/������Ű���Ѵ�. ��� ����ٳ����.
		writeContentBean.setContent_write_idx(loginUserBean.getUser_idx());
		boardDAO.addContentInfo(writeContentBean);

	}

	public String getBoardInfoName(int board_info_idx) {
		return boardDAO.getBoardInfoName(board_info_idx);
	}
//------------------------------����¡----------------------------
	public List<ContentBean> getContentList(int board_info_idx, int page){
		//RowBounds : (start, end)
		/* page 1 - 1 -> 0 + 9 = 9
		 * pag2 2 - 1 -> 10 + 9 = 19  
		 * page 3 -1 -> 20 +9 = 29*/
		int start = (page -1) * page_listcnt;
		RowBounds rowBounds = new RowBounds(start, page_listcnt);
		
		return boardDAO.getContentList(board_info_idx, rowBounds);
	}
	//----------------ī���͸���
	public PageBean getContentCnt(int content_board_idx, int currentPage) {
		//��ü���� ����
		int content_cnt = boardDAO.getContentCnt(content_board_idx);
		
		PageBean pageBean = new PageBean(content_cnt, currentPage, page_listcnt, page_paginationcnt);
		
		return pageBean;
	}


//----------------------------------------------------------------
	public ContentBean getContentInfo(int content_idx) {
		return boardDAO.getContentInfo(content_idx);
	}
	public void modifyContentInfo(ContentBean modifyContentBean) {

		// ÷�����Ͽ� ���� ó��
		MultipartFile upload_file = modifyContentBean.getUpload_file();

		if (upload_file.getSize() > 0) { // upload�� �ִٸ�
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
