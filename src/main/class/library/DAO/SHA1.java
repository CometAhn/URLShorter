package library.DAO;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
public class SHA1 {

	// 비밀번호 암호화
	public String encrypt(String pw, String pwkey) throws Exception {
		String newkey = pwkey + pw + "abcdef";
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1"); // 이 부분을 SHA-256, MD5로만 바꿔주면 된다.
			md.update(newkey.getBytes()); // newkey를 SHA-1으로 변환할 예정!

			byte byteData[] = md.digest();

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	// 랜덤 숫자 생성
	public String randnum() {
		Random random = new Random();
		int length = random.nextInt(5) + 5;

		StringBuffer newWord = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int choice = random.nextInt(3);
			switch (choice) {
				case 0:
					newWord.append((char) ((int) random.nextInt(25) + 97));
					break;
				case 1:
					newWord.append((char) ((int) random.nextInt(25) + 65));
					break;
				case 2:
					newWord.append((char) ((int) random.nextInt(10) + 48));
					break;
				default:
					break;
			}
		}

		return newWord.substring(0);
	}

}
