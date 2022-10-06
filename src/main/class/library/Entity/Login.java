package library.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Login {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String lid;
	private String password;
	@ColumnDefault("0")
	private String token;
	private String name;
	private String gender;
	private String birth;
	private String email;
	private String phone;
	private String address;
	private String registDay;
	private boolean grade;
	private boolean used;
	private String overdue;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "login")
	private List<Review> reviewList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "login")
	private List<Cart> cartList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "login")
	private List<Loan> loanList;
	private int reviewCount;
	private int loanCount;
	private String emailkey;
	private boolean checked;
	private boolean temppw;
}
