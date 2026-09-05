package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;
import vn.iotstar.utils.ValidationUtil;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");

		req.setAttribute("username", username);
		req.setAttribute("fullname", fullname);
		req.setAttribute("email", email);
		req.setAttribute("phone", phone);

		String error = validate(username, password, confirmPassword, fullname, email, phone);
		if (error != null) {
			req.setAttribute("alert", error);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
			return;
		}

		User user = new User();
		user.setUsername(username.trim());
		user.setPassword(password);
		user.setFullname(fullname.trim());
		user.setEmail(email.trim());
		user.setPhone(phone != null ? phone.trim() : null);
		user.setImages(Constant.DEFAULT_FILENAME);

		try {
			userService.register(user);
			resp.sendRedirect(req.getContextPath() + "/verify-otp?username=" + username);
		} catch (Exception e) {
			req.setAttribute("alert", e.getMessage());
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
		}
	}

	private String validate(String username, String password, String confirmPassword, String fullname,
			String email, String phone) {

		if (ValidationUtil.isBlank(username) || ValidationUtil.isBlank(password)
				|| ValidationUtil.isBlank(fullname) || ValidationUtil.isBlank(email)) {
			return "Vui lòng nhập đầy đủ các trường bắt buộc";
		}
		if (!ValidationUtil.isValidUsername(username)) {
			return "Tên đăng nhập phải từ 4-50 ký tự, chỉ gồm chữ, số và dấu gạch dưới";
		}
		if (!ValidationUtil.isValidPassword(password)) {
			return "Mật khẩu phải có ít nhất 6 ký tự";
		}
		if (confirmPassword != null && !password.equals(confirmPassword)) {
			return "Mật khẩu xác nhận không khớp";
		}
		if (!ValidationUtil.isValidFullname(fullname)) {
			return "Họ tên phải từ 2-200 ký tự";
		}
		if (!ValidationUtil.isValidEmail(email)) {
			return "Email không đúng định dạng";
		}
		if (!ValidationUtil.isValidPhone(phone)) {
			return "Số điện thoại không đúng định dạng (VD: 0912345678)";
		}
		return null;
	}
}