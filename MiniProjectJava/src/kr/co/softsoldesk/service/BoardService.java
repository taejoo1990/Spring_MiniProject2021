package kr.co.softsoldesk.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.dao.BoardDao;

@Service
@PropertySource("/WEB-INF/properties/option.properties")
public class BoardService{
	
	@Value("${path.upload}")
	private String path_upload;
	
	@Autowired
	private BoardDao boardDao;
	
	@Resource(name="loginUserBean")
	private UserBean  loginUserBean;
	
	private String saveUploadFile(MultipartFile upload_file) {
		
		//현재시간과 오리지널 파일네임
		String file_name = System.currentTimeMillis()+"_"+upload_file.getName();

		/*
		경로오류솔루션
		String file_name = System.currentTimeMillis() + "_" +  
				FilenameUtils.getBaseName(upload_file.getOriginalFilename()) + "." + 
						FilenameUtils.getExtension(upload_file.getOriginalFilename());
		 */
		try {
			upload_file.transferTo(new File(path_upload+"/"+file_name));//smple/test.png
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return file_name;
	}
	
	public void addContentInfo(ContentBean writeContentBean) {	
		/*
		 * System.out.println(writeContentBean.getContent_subject());
		 * System.out.println(writeContentBean.getContent_text());
		 * System.out.println(writeContentBean.getUpload_file().getSize());
		 */
		MultipartFile upload_file=writeContentBean.getUpload_file();
		
		if(upload_file.getSize()>0) {
			String file_name=saveUploadFile(upload_file);
			System.out.println(file_name);
			
			//첨부화일 호출 
			writeContentBean.setContent_file(file_name);
		}
		writeContentBean.setContent_write_idx(loginUserBean.getUser_idx());
			boardDao.addContentInfo(writeContentBean);
		}
	
		public List<ContentBean> getContentList(int board_info_idx){
		return boardDao.getContentList(board_info_idx);
	}
		
		public String getBoardInfoName(int board_info_idx) {
			return boardDao.getBoardInfoName(board_info_idx);
		}

		
	}



	


