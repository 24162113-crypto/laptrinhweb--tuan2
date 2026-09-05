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

@WebServlet(urlPatterns = { "/forgot-password" })
public class ForgotPasswordController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String email = req.getParameter("email");

		if (email == null || email.trim().isEmpty()) {
			req.setAttribute("alert", "Vui lòng nhập email");
			req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
			return;
		}

		boolean ok = userService.forgotPassword(email.trim());
		if (ok) {
			resp.sendRedirect(req.getContextPath() + "/reset-password?email=" + email);
		} else {
			req.setAttribute("alert", "Email không tồn tại trong hệ thống");
			req.getRequestDispatcher(Constant.Path.FORGOT_PASSWORD).forward(req, resp);
		}
	}
}
