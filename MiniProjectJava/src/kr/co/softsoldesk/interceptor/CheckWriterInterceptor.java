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

	// �ڵ������� �ȵǹǷ� �����ڸ� ���Ͽ� ����
	public CheckWriterInterceptor(UserBean loginUserBean, BoardService boardService) {

		this.loginUserBean = loginUserBean;
		this.boardService = boardService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// read.jsp���� ���Ե� content_idx��������
		String str1 = request.getParameter("content_idx");
		int content_idx = Integer.parseInt(str1); // ����ȯ

		// ���� �Խñ����� ��������
		ContentBean currentContentBean = boardService.getContentInfo(content_idx);

		//�ۼ��� ����� �۹�ȣ�� �α����� ����� ����ڹ�ȣ�� �ٸ��� ������ ���´�
		if (currentContentBean.getContent_write_idx() != loginUserBean.getUser_idx()) {
			//��θ� �о�ͼ�
			String contentPath=request.getContextPath();
			//not_writeȣ��
			response.sendRedirect(contentPath + "/board/not_write");
			
			return false;
		}
		return true;
	}

}
