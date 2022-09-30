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
}
