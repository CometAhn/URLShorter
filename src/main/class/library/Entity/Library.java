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
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booklist")
public class Library {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bid;
	private String title;
	private String writer;
	private String description;
	private String category;
	private String publisher;
	private int stock;
	private String bookCover;
	private String date;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "library") // 이거 테이블 명 or .java명 인지?
	private List<Review> reviewList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "library") // 이거 테이블 명 or .java명 인지?
	private List<Cart> cartList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "library") // 이거 테이블 명 or .java명 인지?
	private List<Loan> loanList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "library") // 이거 테이블 명 or .java명 인지?
	private List<Recommend> recommendList;


/*  이거 loan 테이블임
	private String startDate;
	private String returnDate;
	private String endDate;
	private boolean status;
	private boolean reviewed;
	
	얜 아님
	private int period;
*/
}
