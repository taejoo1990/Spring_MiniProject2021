package kr.co.softsoldesk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.ContentBean;
import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.service.BoardService;

public class CheckWriterInterceptor implements HandlerInterceptor {

	private UserBean loginUserBean;
	private BoardService boardService;

	// 자동주입이 안되므로 생성자를 통하여 구현
	public CheckWriterInterceptor(UserBean loginUserBean, BoardService boardService) {

		this.loginUserBean = loginUserBean;
		this.boardService = boardService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// read.jsp에서 주입된 content_idx가져오기
		String str1 = request.getParameter("content_idx");
		int content_idx = Integer.parseInt(str1); // 형변환

		// 현재 게시글정보 가져오기
		ContentBean currentContentBean = boardService.getContentInfo(content_idx);

		//작성한 사람의 글번호와 로그인한 사람의 사용자번호가 다르면 접근을 막는다
		if (currentContentBean.getContent_write_idx() != loginUserBean.getUser_idx()) {
			//경로를 읽어와서
			String contentPath=request.getContextPath();
			//not_write호출
			response.sendRedirect(contentPath + "/board/not_write");
			
			return false;
		}
		return true;
	}

}
