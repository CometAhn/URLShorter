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
@AllArgsConstructor
@NoArgsConstructor
public class Loan {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Login login;
	@ManyToOne
	private Library library;
	private String startDate;
	private String returnDate;
	private String endDate;
	private int period; // 쓸모 없지만 게터/세터용으로 만듬.
	private boolean status;
	private boolean reviewed;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "loan")
	private List<Review> reviewList;

}
