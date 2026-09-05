package vn.iotstar.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.User;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.utils.Constant;

/**
 * Servlet cho phep user dang nhap xem va cap nhat ho so ca nhan:
 * - Ho ten (fullname)
 * - So dien thoai (phone)
 * - Anh dai dien (images) - upload bang multipart/form-data
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = Constant.MAX_FILE_SIZE, maxRequestSize = Constant.MAX_REQUEST_SIZE)
@WebServlet(urlPatterns = { "/profile" })
public class ProfileController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		User account = getLoggedInUser(req);
		if (account == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		// Lay lai du lieu moi nhat tu CSDL (tranh hien thi du lieu cu trong session)
		User user = userService.findByUsername(account.getUsername());
		req.setAttribute("user", user);
		req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		User account = getLoggedInUser(req);
		if (account == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String fullname = req.getParameter("fullname");
		String phone = req.getParameter("phone");

		try {
			String images = handleUpload(req, account.getImages());
			User updated = userService.updateProfile(account.getId(), fullname, phone, images);

			// Dong bo lai thong tin trong session sau khi cap nhat thanh cong
			req.getSession(true).setAttribute(Constant.SESSION_ACCOUNT, updated);

			req.setAttribute("notice", "Cập nhật hồ sơ thành công");
			req.setAttribute("user", updated);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("alert", "Cập nhật hồ sơ thất bại: " + e.getMessage());
			req.setAttribute("user", account);
		}

		req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
	}

	private User getLoggedInUser(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null) {
			return null;
		}
		return (User) session.getAttribute(Constant.SESSION_ACCOUNT);
	}

	/**
	 * Xu ly upload file anh dai dien (multipart). Neu khong chon file moi thi
	 * giu nguyen ten file cu.
	 */
	private String handleUpload(HttpServletRequest req, String oldFileName) {
		String uploadPath = Constant.DIR;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		try {
			Part part = req.getPart("images");
			if (part != null && part.getSize() > 0 && part.getSubmittedFileName() != null
					&& !part.getSubmittedFileName().trim().isEmpty()) {
				String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				int index = filename.lastIndexOf(".");
				String ext = filename.substring(index + 1);
				String fname = "user_" + System.currentTimeMillis() + "." + ext;
				part.write(uploadPath + "/" + fname);
				return fname;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldFileName != null ? oldFileName : Constant.DEFAULT_FILENAME;
	}
}
