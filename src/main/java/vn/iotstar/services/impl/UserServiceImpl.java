package vn.iotstar.services.impl;

import java.sql.Timestamp;
import java.time.Instant;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.impl.UserDao;
import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.utils.MailUtil;
import vn.iotstar.utils.OtpUtil;

public class UserServiceImpl implements IUserService {

	private IUserDao userDao = new UserDao();

	@Override
	public User login(String username, String password) {
		User user = userDao.findByUsername(username);
		if (user != null && user.getPassword().equals(password) && user.getActive() == 1) {
			return user;
		}
		return null;
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User register(User user) throws Exception {
		if (userDao.findByUsername(user.getUsername()) != null) {
			throw new Exception("Tên đăng nhập đã tồn tại");
		}
		if (userDao.findByEmail(user.getEmail()) != null) {
			throw new Exception("Email đã được sử dụng");
		}

		user.setActive(0);
		user.setRoleid(1); 
		user.setCreateDate(Timestamp.from(Instant.now()));

		String otp = OtpUtil.generateOtp();
		user.setOtp(otp);
		user.setOtpExpiry(OtpUtil.generateExpiry());

		userDao.insert(user);

		MailUtil.sendOtpActiveAccount(user.getEmail(), otp);

		return user;
	}

	@Override
	public boolean verifyActiveOtp(String username, String otp) {
		User user = userDao.findByUsername(username);
		if (user == null || user.getActive() == 1) {
			return false;
		}
		if (user.getOtp() == null || !user.getOtp().equals(otp)) {
			return false;
		}
		if (OtpUtil.isExpired(user.getOtpExpiry())) {
			return false;
		}

		user.setActive(1);
		user.setOtp(null);
		user.setOtpExpiry(null);
		userDao.update(user);
		return true;
	}

	@Override
	public boolean resendActiveOtp(String username) {
		User user = userDao.findByUsername(username);
		if (user == null || user.getActive() == 1) {
			return false;
		}
		String otp = OtpUtil.generateOtp();
		user.setOtp(otp);
		user.setOtpExpiry(OtpUtil.generateExpiry());
		userDao.update(user);
		return MailUtil.sendOtpActiveAccount(user.getEmail(), otp);
	}

	@Override
	public boolean forgotPassword(String email) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			return false;
		}
		String otp = OtpUtil.generateOtp();
		user.setOtp(otp);
		user.setOtpExpiry(OtpUtil.generateExpiry());
		userDao.update(user);
		return MailUtil.sendOtpForgotPassword(email, otp);
	}

	@Override
	public boolean resetPassword(String email, String otp, String newPassword) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			return false;
		}
		if (user.getOtp() == null || !user.getOtp().equals(otp)) {
			return false;
		}
		if (OtpUtil.isExpired(user.getOtpExpiry())) {
			return false;
		}

		user.setPassword(newPassword);
		user.setOtp(null);
		user.setOtpExpiry(null);
		userDao.update(user);
		return true;
	}

	@Override
	public User updateProfile(int id, String fullname, String phone, String images) throws Exception {
		User user = userDao.findById(id);
		if (user == null) {
			throw new Exception("Người dùng không tồn tại");
		}

		if (fullname != null) {
			user.setFullname(fullname.trim());
		}
		if (phone != null) {
			user.setPhone(phone.trim());
		}
		if (images != null && !images.trim().isEmpty()) {
			user.setImages(images);
		}

		userDao.update(user);
		return user;
	}
}
