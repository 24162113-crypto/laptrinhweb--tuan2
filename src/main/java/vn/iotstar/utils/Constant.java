package vn.iotstar.utils;

public class Constant {

	public static final String SESSION_ACCOUNT = "account";
	public static final String COOKIE_REMEMBER = "username";

	public static final String DIR = "D:\\upload";
	public static final String DEFAULT_FILENAME = "avata.png";

	public static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
	public static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

	//Cau hinh OTP
	public static final int OTP_LENGTH = 6;
	public static final long OTP_EXPIRE_MINUTES = 5; // OTP het han sau 5 phut


	public static final String MAIL_SMTP_HOST = "smtp.gmail.com";
	public static final String MAIL_SMTP_PORT = "587";
	public static final String MAIL_USERNAME = "";//mail user
	public static final String MAIL_PASSWORD = "";//mail app password
	public static final String MAIL_FROM_NAME = "JPA LT Web";

	//Phan trang san pham
	public static final int PRODUCT_PAGE_SIZE = 6;
	public static final int PRODUCT_HOME_LATEST = 10;

	public static class Path {
		public static final String LOGIN = "/views/login.jsp";
		public static final String REGISTER = "/views/register.jsp";
		public static final String VERIFY_OTP = "/views/verify-otp.jsp";
		public static final String FORGOT_PASSWORD = "/views/forgot-password.jsp";
		public static final String RESET_PASSWORD = "/views/reset-password.jsp";
		public static final String HOME = "/views/home.jsp";
	}
}
