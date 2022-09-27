/**
 * 책 DTO int bid				아이디 String title			제목 String writer		작가명 String description	설명 String
 * category		카테고리 String publisher		출판사 int stock			개수 String bookcover		이미지 String date			등록 날짜
 * <p>
 * 책 반납 관련 변수도 여기에 둠. String startdate		대여 날짜 String enddate		반납 기한 String returndate	반납한 날짜 boolean
 * status		상태 : (0 = 반납, 1 = 대여) - 대여 기록은 남겨두기 위해 대여 테이블 데이터는 지우지 않음.
 */

package library.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllinOne {
	// 좋지 못한 방법
	// 쓰지 말자! 일단 오류나니까 냅두기.
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

	// 얜 컬럼은 아닌데 기간 저장용으로 씀
	private int period;
	// Library, Loan 짬뽕
}
