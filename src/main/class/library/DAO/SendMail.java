package library.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Component
public class SendMail {

	@Autowired
	private JavaMailSender mailSender;

	// 메일 보내기
	public String sendmail(String id, String email) throws Exception {

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

		String key = newWord.substring(0);

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

			messageHelper.setSubject(id + "님 인증 번호입니다.");
			messageHelper.setText("인증번호는 " + newWord + " 입니다.");
			messageHelper.setTo(email);
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
			mailSender.send(msg);


		} catch (
				MessagingException e) {
			System.out.println("MessagingException");
			e.printStackTrace();
		}

		return key;
	}
}
