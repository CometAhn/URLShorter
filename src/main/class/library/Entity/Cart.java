/**
 * * 책 장바구니 DTO
 * String id 	회원 아이디.
 * int bid 	책 아이디.
 * <p>
 * * 데이터베이스(cart)
 * varchar ID	회원 아이디
 * int BID	 	책 아이디
 */

package library.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Login login;
	@ManyToOne
	private Library library;
}
