package vn.iotstar.utils;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class OtpUtil {

	private static final SecureRandom random = new SecureRandom();

	public static String generateOtp() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Constant.OTP_LENGTH; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	public static Timestamp generateExpiry() {
		Instant expiry = Instant.now().plus(Constant.OTP_EXPIRE_MINUTES, ChronoUnit.MINUTES);
		return Timestamp.from(expiry);
	}

	public static boolean isExpired(Timestamp expiry) {
		if (expiry == null) {
			return true;
		}
		return Timestamp.from(Instant.now()).after(expiry);
	}
}
