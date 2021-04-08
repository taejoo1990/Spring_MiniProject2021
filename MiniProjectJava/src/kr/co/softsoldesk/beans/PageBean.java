package kr.co.softsoldesk.beans;

public class PageBean {
	
	// �ּ� ������ ��ȣ
	private int min;
	// �ִ� ������ ��ȣ
	private int max;
	// ���� ��ư�� ������ ��ȣ
	private int prevPage;
	// ���� ��ư�� ������ ��ȣ
	private int nextPage;
	// ��ü ������ ����
	private int pageCnt;
	// ���� ������ ��ȣ
	private int currentPage;
	
	
	// contentCnt : ��ü�� ����(oracle), currentPage(oracle) : ���� �� ��ȣ, 
	//contentPageCnt : �������� ���� ����(option.property), 
	//paginationCnt(option.property) : ������ ��ư�� ����
	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		
		// ���� ������ ��ȣ
		this.currentPage = currentPage;
		
		// ��ü ������ ����
		pageCnt = contentCnt / contentPageCnt;
		//533/10=530...3
		if(contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}
		/*���̴� ������ ��ư
		1~10:1(�ּ�),10(�ִ�)
		11~20:11,20
		21~30:21,30
		
		//-1
		 0 ~ 9:0
		 10~19:10
		 20~29:20
		 
		//�������� ���� ����(10)�� ������
		 0 : 0
		 1 : 1
		 2 : 2
		 
		//�������� ���� ����(10) ���ϱ�
		 0:0
		 10:10
		 20:20
		 
		//+1
		 1:1
		 11:11
		 21:21
		*/
		min = ((currentPage - 1) / contentPageCnt) * contentPageCnt + 1;
		max = min + paginationCnt - 1; //1+10=11�̹Ƿ� 1�� ����
		//10���� ��� ���
		if(max > pageCnt) {
			max = pageCnt;
		}
		
		prevPage = min - 1; //������ư
		nextPage = currentPage + 1; //������ư
		//���������̸� �Ѿ�� �ʵ���
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
