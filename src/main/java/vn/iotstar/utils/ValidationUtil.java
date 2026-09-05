package vn.iotstar.utils;

import java.util.regex.Pattern;

public class ValidationUtil {

	private static final Pattern EMAIL_PATTERN = Pattern
			.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

	private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9,10}$");

	private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{4,50}$");

	public static boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	public static boolean isValidEmail(String email) {
		return !isBlank(email) && EMAIL_PATTERN.matcher(email.trim()).matches();
	}

	public static boolean isValidPhone(String phone) {
		if (isBlank(phone)) {
			return true;
		}
		return PHONE_PATTERN.matcher(phone.trim()).matches();
	}

	public static boolean isValidUsername(String username) {
		return !isBlank(username) && USERNAME_PATTERN.matcher(username.trim()).matches();
	}

	public static boolean isValidPassword(String password) {
		return !isBlank(password) && password.length() >= 6;
	}

	public static boolean isValidFullname(String fullname) {
		return !isBlank(fullname) && fullname.trim().length() >= 2 && fullname.trim().length() <= 200;
	}

	public static String validateImageFile(String originalFilename, long fileSize, long maxSize) {
		if (isBlank(originalFilename)) {
			return null;
		}
		String lower = originalFilename.toLowerCase();
		if (!(lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png")
				|| lower.endsWith(".gif") || lower.endsWith(".webp"))) {
			return "Chỉ chấp nhận file ảnh định dạng JPG, PNG, GIF, WEBP";
		}
		if (fileSize > maxSize) {
			return "Kích thước ảnh vượt quá giới hạn cho phép (" + (maxSize / (1024 * 1024)) + "MB)";
		}
		return null;
	}
}