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
	public void sendmail(String id, String email, String key) throws Exception {


		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

			messageHelper.setSubject("[it Lib]" + id + "님 인증 번호입니다.");
			messageHelper.setText("인증번호는 " + key + " 입니다.");
			messageHelper.setTo(email);
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
			mailSender.send(msg);


		} catch (
				MessagingException e) {
			System.out.println("MessagingException");
			e.printStackTrace();
		}

	}

	// 비밀번호 보내기용
	public void sendmailpw(String id, String email, String key) throws Exception {

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

			messageHelper.setSubject("[it Lib]" + id + "님 임시 비밀번호입니다.");
			messageHelper.setText("임시 비밀번호는 " + key + " 입니다.");
			messageHelper.setTo(email);
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
			mailSender.send(msg);


		} catch (
				MessagingException e) {
			System.out.println("MessagingException");
			e.printStackTrace();
		}

	}
}
