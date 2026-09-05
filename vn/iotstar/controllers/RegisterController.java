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
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");

		if (isBlank(username) || isBlank(password) || isBlank(email)) {
			req.setAttribute("alert", "Vui lòng nhập đầy đủ Tên đăng nhập, Mật khẩu và Email");
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
			return;
		}

		User user = new User();
		user.setUsername(username.trim());
		user.setPassword(password);
		user.setFullname(fullname);
		user.setEmail(email.trim());
		user.setPhone(phone);
		user.setImages(Constant.DEFAULT_FILENAME);

		try {
			userService.register(user);
			resp.sendRedirect(req.getContextPath() + "/verify-otp?username=" + username);
		} catch (Exception e) {
			req.setAttribute("alert", e.getMessage());
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
		}
	}

	private boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}
}
