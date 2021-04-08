package kr.co.softsoldesk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.beans.PageBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.BoardService;

@Controller
@RequestMapping("/board")//이 컨트롤러에서는 /board를 고정해서 받음
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;
	//메인+페이징------------------------------------------
	@GetMapping("/main")
	public String main(@RequestParam("board_info_idx") int board_info_idx,
					   @RequestParam(value = "page", defaultValue = "1") int page,Model model) {
		
		model.addAttribute("board_info_idx",board_info_idx);
		String boardInfoName=boardService.getBoardInfoName(board_info_idx);
		model.addAttribute("boardInfoName", boardInfoName);
																				//페이징
		List<ContentBean> contentList = boardService.getContentList(board_info_idx, page);
		model.addAttribute("contentList", contentList);
		
		PageBean pageBean = boardService.getContentCnt(board_info_idx, page);
		model.addAttribute("pageBean", pageBean);
		
		model.addAttribute("page", page);

		return "board/main";
	}
	
	//----------------------------------------------------
	@GetMapping("/read")
	public String read(@RequestParam("board_info_idx") int board_info_idx,
					   @RequestParam("content_idx") int content_idx,
					   @RequestParam("page") int page,
					   Model model) {
		model.addAttribute("board_info_idx",board_info_idx);
		model.addAttribute("content_idx",content_idx);
		
		ContentBean readContentBean=boardService.getContentInfo(content_idx);
		model.addAttribute("readContentBean",readContentBean);
		model.addAttribute("loginUserBean",loginUserBean);
		
		model.addAttribute("page", page);

		return "board/read";
	}

	//==============껍데기객체생성해서->뷰단가서 받아와서->DB가서 물어와서->처리한다=============================
	/*보드컨트롤러에서 이제는, 글쓴것을 가지고 board의 write.jsp로 가야하므로 모델끌고가서 뷰단에서 폼폼써야한다
	 * ----글쓰기에서 사용하는 빈은 ContentBean*/
	@GetMapping("/write")
	public String write(@ModelAttribute("writeContentBean") ContentBean writeContentBean,
			            @RequestParam("board_info_idx") int board_info_idx) {
		
		//board_main으로부터 '글쓰기'누르면 히든으로 붙어오는 board_info_idx를 파라미터로 받아와서, 어느게시판에 글쓰는것인지 번호 설정.
		writeContentBean.setContent_board_idx(board_info_idx);
		return "board/write";
	}
	
	//ContentBean에 유효성검사할 필드 설정
	@PostMapping("/write_pro")
	public String write_pro(@Valid @ModelAttribute("writeContentBean") ContentBean writeContentBean,BindingResult result) {
		
		if(result.hasErrors()) {
			
			return "board/write";
		}
		//유효성검사는 이제 else로 통과했고, 서비스의 컨텐트입력메서드 돌리면된다. 
		boardService.addContentInfo(writeContentBean);
		
		return "board/write_success";
	}
	
	//===============================
	@GetMapping("/modify")
	public String modify(@RequestParam("board_info_idx") int board_info_idx,
			  			@RequestParam("content_idx") int content_idx,
			  			@ModelAttribute("modifyContentBean") ContentBean modifyContentBean,
			  			@RequestParam("page") int page,
			  			Model model) {
		
		model.addAttribute("board_info_idx", board_info_idx);
		model.addAttribute("content_idx", content_idx);
		
		//게시글 하나의 정보 가져오기
		ContentBean tempContentBean = boardService.getContentInfo(content_idx);
		
		modifyContentBean.setContent_write_name(tempContentBean.getContent_write_name());
		modifyContentBean.setContent_date(tempContentBean.getContent_date());
		modifyContentBean.setContent_subject(tempContentBean.getContent_subject());
		modifyContentBean.setContent_text(tempContentBean.getContent_text());
		modifyContentBean.setContent_file(tempContentBean.getContent_file());
		modifyContentBean.setContent_write_idx(tempContentBean.getContent_write_idx());
		modifyContentBean.setContent_board_idx(board_info_idx); //param값 그대로 가져오기
		modifyContentBean.setContent_idx(content_idx); //param값 그대로 가져오기
		
		model.addAttribute("page", page);

		return "board/modify";
	}
	@PostMapping("/modify_pro")
	public String modify_pro(@Valid @ModelAttribute("modifyContentBean") ContentBean modifyContentBean, 
							 BindingResult result,Model model,
							 @RequestParam("page") int page) {
		if(result.hasErrors()) {
			return "board/modify";
		}
		
		boardService.modifyContentInfo(modifyContentBean);
		model.addAttribute("page", page);

		return "board/modify_success";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("board_info_idx") int board_info_idx,
			 			@RequestParam("content_idx") int content_idx,
			 			Model model) {
		
		boardService.deleteContentInfo(content_idx);
		//해당하는 글목록 게시판으로 이동
		model.addAttribute("board_info_idx", board_info_idx);
		
		return "board/delete";
	}

	@GetMapping("/not_write")
	public String not_write() {
		return "board/not_write";
	}
}
