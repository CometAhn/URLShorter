package library.DAO;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA1 {

	// 비밀번호 암호화
	public String encrypt(String pw) throws Exception {
		String retVal = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1"); // 이 부분을 SHA-256, MD5로만 바꿔주면 된다.
			md.update(pw.getBytes()); // "세이프123"을 SHA-1으로 변환할 예정!

			byte byteData[] = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			retVal = sb.toString();
			System.out.println(retVal); // 결과물이 출력됨. aed19017dbb4d25a580b7f9e012e29be089bd1f3
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return retVal;
	}
}
