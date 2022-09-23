/**
 * 책 DTO int bid				아이디 String title			제목 String writer		작가명 String description	설명 String
 * category		카테고리 String publisher		출판사 int stock			개수 String bookcover		이미지 String date			등록 날짜
 * <p>
 * 책 반납 관련 변수도 여기에 둠. String startdate		대여 날짜 String enddate		반납 기한 String returndate	반납한 날짜 boolean
 * status		상태 : (0 = 반납, 1 = 대여) - 대여 기록은 남겨두기 위해 대여 테이블 데이터는 지우지 않음.
 */

package library;

public class Library {

	private int bid;
	private String title;
	private String writer;
	private String description;
	private String category;
	private String publisher;
	private int stock;
	private String bookCover;
	private String date;

	private String startDate;
	private String returnDate;
	private String endDate;
	private boolean status;

	private boolean reviewed;


	public boolean isReviewed() {
		return reviewed;
	}

	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}

	private int period;

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getBookCover() {
		return bookCover;
	}

	public void setBookCover(String bookCover) {
		this.bookCover = bookCover;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
