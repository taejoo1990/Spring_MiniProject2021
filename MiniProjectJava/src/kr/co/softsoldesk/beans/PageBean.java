package kr.co.softsoldesk.beans;

public class PageBean {
	
	// 최소 페이지 번호
	private int min;
	// 최대 페이지 번호
	private int max;
	// 이전 버튼의 페이지 번호
	private int prevPage;
	// 다음 버튼의 페이지 번호
	private int nextPage;
	// 전체 페이지 개수
	private int pageCnt;
	// 현재 페이지 번호
	private int currentPage;
	
	
	// contentCnt : 전체글 개수(oracle), currentPage(oracle) : 현재 글 번호, 
	//contentPageCnt : 페이지당 글의 개수(option.property), 
	//paginationCnt(option.property) : 페이지 버튼의 개수
	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		
		// 현재 페이지 번호
		this.currentPage = currentPage;
		
		// 전체 페이지 개수
		pageCnt = contentCnt / contentPageCnt;
		//533/10=530...3
		if(contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}
		/*보이는 페이지 버튼
		1~10:1(최소),10(최대)
		11~20:11,20
		21~30:21,30
		
		//-1
		 0 ~ 9:0
		 10~19:10
		 20~29:20
		 
		//페이지당 글의 갯수(10)로 나누기
		 0 : 0
		 1 : 1
		 2 : 2
		 
		//페이지당 글의 갯수(10) 곱하기
		 0:0
		 10:10
		 20:20
		 
		//+1
		 1:1
		 11:11
		 21:21
		*/
		min = ((currentPage - 1) / contentPageCnt) * contentPageCnt + 1;
		max = min + paginationCnt - 1; //1+10=11이므로 1을 빼줌
		//10개씩 끊어서 사용
		if(max > pageCnt) {
			max = pageCnt;
		}
		
		prevPage = min - 1; //이정버튼
		nextPage = currentPage + 1; //다음버튼
		//마지막페이를 넘어가지 않도록
		if(nextPage > pageCnt) {
			nextPage = pageCnt;
		}
	}
	
	public int getMin() {
		return min;
	}
	public int getMax() {
		return max;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	
	
}
