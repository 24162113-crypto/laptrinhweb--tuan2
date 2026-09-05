package vn.iotstar.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.IProductService;
import vn.iotstar.services.impl.CategoryServiceImpl;
import vn.iotstar.services.impl.ProductServiceImpl;
import vn.iotstar.utils.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/admin/products", "/admin/product/add", "/admin/product/insert",
		"/admin/product/edit", "/admin/product/update", "/admin/product/delete" })
public class ProductController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IProductService productService = new ProductServiceImpl();
	private ICategoryService categoryService = new CategoryServiceImpl();

	// Hàm kiểm tra phân quyền Admin
	private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(false);
		Object obj = (session != null) ? session.getAttribute("account") : null;
		if (obj == null && session != null) {
			obj = session.getAttribute("user");
		}

		// 1. Chưa đăng nhập -> Chuyển hướng về trang Login
		if (obj == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return false;
		}

		// 2. Kiểm tra Role (Đã sửa lỗi không tồn tại phương thức getIsAdmin)
		if (obj instanceof User) {
			User user = (User) obj;
			// Giả định roleid = 1 là Admin
			if (user.getRoleid() == 2) {
				return true;
			}
		}

		// 3. Đã đăng nhập nhưng không phải Admin -> Đẩy về trang Home
		resp.sendRedirect(req.getContextPath() + "/home");
		return false;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!checkAdmin(req, resp)) {
			return;
		}

		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if (url.contains("products")) {
			List<Product> list = productService.findAll();
			req.setAttribute("products", list);
			req.setAttribute("listproduct", list);
			req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
		} else if (url.contains("add")) {
			List<Category> categories = categoryService.findAll();
			req.setAttribute("categories", categories);
			req.setAttribute("listcate", categories);
			req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
		} else if (url.contains("edit")) {
			String idStr = req.getParameter("id");
			if (idStr != null && !idStr.isEmpty()) {
				int id = Integer.parseInt(idStr);
				Product product = productService.findById(id);
				List<Category> categories = categoryService.findAll();
				req.setAttribute("product", product);
				req.setAttribute("categories", categories);
				req.setAttribute("listcate", categories);
			}
			req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
		} else if (url.contains("delete")) {
			String idStr = req.getParameter("id");
			if (idStr != null && !idStr.isEmpty()) {
				try {
					productService.delete(Integer.parseInt(idStr));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!checkAdmin(req, resp)) {
			return;
		}

		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if (url.contains("insert")) {
			Product product = new Product();
			bindFromRequest(req, product);
			product.setCreateDate(Timestamp.from(Instant.now()));
			product.setImages(handleUpload(req, null));

			productService.insert(product);
			resp.sendRedirect(req.getContextPath() + "/admin/products");

		} else if (url.contains("update")) {
			String idStr = getParam(req, "productId", "productid");
			if (idStr != null && !idStr.isEmpty()) {
				int productid = Integer.parseInt(idStr);
				Product old = productService.findById(productid);

				Product product = new Product();
				product.setProductid(productid);
				bindFromRequest(req, product);
				product.setCreateDate(old != null ? old.getCreateDate() : Timestamp.from(Instant.now()));
				product.setImages(handleUpload(req, old != null ? old.getImages() : null));

				productService.update(product);
			}
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}

	private void bindFromRequest(HttpServletRequest req, Product product) {
		product.setProductname(getParam(req, "productName", "productname"));
		product.setDescription(req.getParameter("description"));

		try {
			String priceStr = req.getParameter("price");
			product.setPrice(priceStr != null && !priceStr.isEmpty() ? Double.parseDouble(priceStr) : 0.0);
		} catch (Exception e) {
			product.setPrice(0.0);
		}

		try {
			String qtyStr = req.getParameter("quantity");
			product.setQuantity(qtyStr != null && !qtyStr.isEmpty() ? Integer.parseInt(qtyStr) : 0);
		} catch (Exception e) {
			product.setQuantity(0);
		}

		try {
			String statusStr = req.getParameter("status");
			product.setStatus(statusStr != null && !statusStr.isEmpty() ? Integer.parseInt(statusStr) : 1);
		} catch (Exception e) {
			product.setStatus(1);
		}

		try {
			String cateIdStr = getParam(req, "categoryId", "categoryid");
			if (cateIdStr != null && !cateIdStr.isEmpty()) {
				int categoryid = Integer.parseInt(cateIdStr);
				Category category = categoryService.findById(categoryid);
				product.setCategory(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String handleUpload(HttpServletRequest req, String oldFileName) {
		String imageUrlParam = req.getParameter("images");
		if (imageUrlParam != null && !imageUrlParam.trim().isEmpty() && imageUrlParam.startsWith("http")) {
			return imageUrlParam.trim();
		}

		String uploadPath = Constant.DIR;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		try {
			Part part = req.getPart("imageFile");
			if (part == null || part.getSize() == 0) {
				part = req.getPart("images");
			}

			if (part != null && part.getSize() > 0) {
				String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				int index = filename.lastIndexOf(".");
				if (index > 0) {
					String ext = filename.substring(index + 1);
					String fname = System.currentTimeMillis() + "." + ext;
					part.write(uploadPath + File.separator + fname);
					return fname;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (oldFileName != null && !oldFileName.isEmpty()) ? oldFileName : Constant.DEFAULT_FILENAME;
	}

	private String getParam(HttpServletRequest req, String primaryKey, String secondaryKey) {
		String val = req.getParameter(primaryKey);
		if (val == null || val.trim().isEmpty()) {
			val = req.getParameter(secondaryKey);
		}
		return val;
	}
}