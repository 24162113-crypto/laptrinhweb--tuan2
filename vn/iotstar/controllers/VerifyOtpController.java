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

@WebServlet(urlPatterns = { "/verify-otp", "/resend-otp" })
public class VerifyOtpController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		String username = req.getParameter("username");

		if (url.contains("resend-otp")) {
			boolean ok = userService.resendActiveOtp(username);
			req.setAttribute("username", username);
			req.setAttribute(ok ? "notice" : "alert",
					ok ? "Đã gửi lại mã OTP mới vào email của bạn" : "Không thể gửi lại OTP");
			req.getRequestDispatcher(Constant.Path.VERIFY_OTP).forward(req, resp);
			return;
		}

		req.setAttribute("username", username);
		req.getRequestDispatcher(Constant.Path.VERIFY_OTP).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String otp = req.getParameter("otp");

		boolean ok = userService.verifyActiveOtp(username, otp);
		if (ok) {
			resp.sendRedirect(req.getContextPath() + "/login");
		} else {
			req.setAttribute("username", username);
			req.setAttribute("alert", "Mã OTP không đúng hoặc đã hết hạn");
			req.getRequestDispatcher(Constant.Path.VERIFY_OTP).forward(req, resp);
		}
	}
}
