package vn.iotstar.services;

import vn.iotstar.entity.User;

public interface IUserService {

	User login(String username, String password);

	User findByUsername(String username);

	User findByEmail(String email);

	
	User register(User user) throws Exception;

	boolean verifyActiveOtp(String username, String otp);

	boolean resendActiveOtp(String username);

	boolean forgotPassword(String email);

	boolean resetPassword(String email, String otp, String newPassword);

	User updateProfile(int id, String fullname, String phone, String images) throws Exception;
}
