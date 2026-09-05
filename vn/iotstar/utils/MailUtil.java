package vn.iotstar.utils;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailUtil {

	private static Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", Constant.MAIL_SMTP_HOST);
		props.put("mail.smtp.port", Constant.MAIL_SMTP_PORT);

		return Session.getInstance(props, new jakarta.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constant.MAIL_USERNAME, Constant.MAIL_PASSWORD);
			}
		});
	}

	public static boolean sendMail(String toEmail, String subject, String content) {
		try {
			Session session = getSession();
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Constant.MAIL_USERNAME, Constant.MAIL_FROM_NAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setContent(content, "text/html; charset=UTF-8");

			Transport.send(message);
			return true;
		} catch (MessagingException | java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean sendOtpActiveAccount(String toEmail, String otp) {
		String subject = "Mã OTP kích hoạt tài khoản";
		String content = "<p>Xin chào,</p>"
				+ "<p>Mã OTP để kích hoạt tài khoản của bạn là: <b style=\"font-size:20px\">" + otp + "</b></p>"
				+ "<p>Mã có hiệu lực trong " + Constant.OTP_EXPIRE_MINUTES + " phút.</p>"
				+ "<p>Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.</p>";
		return sendMail(toEmail, subject, content);
	}

	public static boolean sendOtpForgotPassword(String toEmail, String otp) {
		String subject = "Mã OTP đặt lại mật khẩu";
		String content = "<p>Xin chào,</p>"
				+ "<p>Mã OTP để đặt lại mật khẩu của bạn là: <b style=\"font-size:20px\">" + otp + "</b></p>"
				+ "<p>Mã có hiệu lực trong " + Constant.OTP_EXPIRE_MINUTES + " phút.</p>"
				+ "<p>Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.</p>";
		return sendMail(toEmail, subject, content);
	}
}
