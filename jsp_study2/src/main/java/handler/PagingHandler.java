package handler;

import domain.PagingVO;

public class PagingHandler {
	// list 하단에 나오는 페이지네이션 핸들링 클래스
	private int startPage; // 현재 화면에서 보여줄 시작 페이지네이션 번호 (1 11 21 ...)
	private int endPage; // 현재 화면에서 보여줄 끝 페이지네이션 번호 (10 20 30 ...)
	private int realEndPage; // 실제 전체 리스트의 마지막 페이지 번호
	private boolean prev; // 이전 페이지의 존재 여부 (1 X / 11 21 ... O)
	private boolean next; // 다음 페이지의 존재 여부 (realEngPage X)
	
	private PagingVO pgvo; // 파라미터로 현재 사용자가 클릭한 값을 받아오기
	private int totalCount; // db에서 검색해오기 - 컨트롤러에서 받아오기
	
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		// 1 ~ 10 / 11 ~ 20 / 21 ~ 30 ... 
		// 1 2 3 4 5 6 7 8 9 10 => 10
		// 1 ~ 10 /10 => 0.1 (올림) => 1 * 10 => 10
		// 11 ... 20 => 20 
		// 11 ~ 20 /10 => 1.1 (올림) => 2 * 10 => 20
		// 정수 / 정수 = 정수 0.1 형변환 필요
		this.endPage = (int)Math.ceil(pgvo.getPageNo() / (double)10)*10;
		
		this.startPage = this.endPage - 9;
		
		// 전체 게시글 수 / 한 화면에 표시되는 게시글 수
		// 103 103/10 => 10.3 => 11.0 (올림)
		// 나머지 게시글이 하나라도 있다면 1페이지가 더 생겨야함.
		this.realEndPage = (int)Math.ceil(totalCount / (double)pgvo.getQty());
		
		// 1~10 / 11~20
		// 실제 realEndPage가 11페이지라면 11 끝
		// realEndPage가 endPage와 같지 않을 경우
		if(this.realEndPage < this.endPage) {
			this.endPage = this.realEndPage;
		}
		
		// 이전 다음 유무
		this.prev = this.startPage > 1;
		this.next = this.endPage < this.realEndPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getRealEndPage() {
		return realEndPage;
	}

	public void setRealEndPage(int realEndPage) {
		this.realEndPage = realEndPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public PagingVO getPgvo() {
		return pgvo;
	}

	public void setPgvo(PagingVO pgvo) {
		this.pgvo = pgvo;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "PagingHandler [startPage=" + startPage + ", endPage=" + endPage + ", realEndPage=" + realEndPage
				+ ", prev=" + prev + ", next=" + next + ", pgvo=" + pgvo + ", totalCount=" + totalCount + "]";
	}
	
	
}
