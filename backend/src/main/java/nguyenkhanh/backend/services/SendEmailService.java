package nguyenkhanh.backend.services;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {
	private final static Logger LOGGER = LoggerFactory.getLogger(SendEmailService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendResetPassword(String to, String content) {
		sendEMail(to, "Reset Password", content);
	}

	public void sendConfirmEmail(String to, String content) {
		sendEMail(to, "Confirm your email", content);
	}

	public void sendEMail(String to, String title, String content) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			helper.setSubject(title);
			helper.setFrom("MySystem@gmail.com");
			helper.setTo(to);
			helper.setText(content, true);

			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.error("Failed to send email", e);
			throw new IllegalStateException("Failed to send email");
		}
	}
}
