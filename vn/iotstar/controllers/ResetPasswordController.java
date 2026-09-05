package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = { "/reset-password" })
public class ResetPasswordController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("email", req.getParameter("email"));
		req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String email = req.getParameter("email");
		String otp = req.getParameter("otp");
		String newPassword = req.getParameter("newPassword");
		String confirmPassword = req.getParameter("confirmPassword");

		if (newPassword == null || !newPassword.equals(confirmPassword)) {
			req.setAttribute("email", email);
			req.setAttribute("alert", "Mật khẩu xác nhận không khớp");
			req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
			return;
		}

		boolean ok = userService.resetPassword(email, otp, newPassword);
		if (ok) {
			resp.sendRedirect(req.getContextPath() + "/login");
		} else {
			req.setAttribute("email", email);
			req.setAttribute("alert", "Mã OTP không đúng hoặc đã hết hạn");
			req.getRequestDispatcher(Constant.Path.RESET_PASSWORD).forward(req, resp);
		}
	}
}
