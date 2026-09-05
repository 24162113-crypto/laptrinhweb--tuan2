package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		boolean isRememberMe = "on".equals(remember);

		if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
			req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
			return;
		}

		User existed = userService.findByUsername(username);
		if (existed != null && existed.getActive() == 0) {
			req.setAttribute("alert", "Tài khoản chưa được kích hoạt. Vui lòng xác thực OTP đã gửi qua email.");
			req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
			return;
		}

		User user = userService.login(username, password);
		if (user != null) {
			HttpSession session = req.getSession(true);
			session.setAttribute(Constant.SESSION_ACCOUNT, user);
			if (isRememberMe) {
				Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
				cookie.setMaxAge(30 * 60);
				resp.addCookie(cookie);
			}
			if (user.getRoleid() == 2) {
				resp.sendRedirect(req.getContextPath() + "/admin/products");
			} else {
				resp.sendRedirect(req.getContextPath() + "/home");
			}
		} else {
			req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
			req.getRequestDispatcher(Constant.Path.LOGIN).forward(req, resp);
		}
	}
}
