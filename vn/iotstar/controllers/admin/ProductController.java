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
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if (url.contains("products")) {
			List<Product> list = productService.findAll();
			req.setAttribute("listproduct", list);
			req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
		} else if (url.contains("add")) {
			req.setAttribute("listcate", categoryService.findAll());
			req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
		} else if (url.contains("edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Product product = productService.findById(id);
			req.setAttribute("product", product);
			req.setAttribute("listcate", categoryService.findAll());
			req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
		} else if (url.contains("delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				productService.delete(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
			int productid = Integer.parseInt(req.getParameter("productid"));
			Product old = productService.findById(productid);

			Product product = new Product();
			product.setProductid(productid);
			bindFromRequest(req, product);
			product.setCreateDate(old != null ? old.getCreateDate() : Timestamp.from(Instant.now()));
			product.setImages(handleUpload(req, old != null ? old.getImages() : null));

			productService.update(product);
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}

	private void bindFromRequest(HttpServletRequest req, Product product) {
		product.setProductname(req.getParameter("productname"));
		product.setDescription(req.getParameter("description"));

		try {
			product.setPrice(Double.parseDouble(req.getParameter("price")));
		} catch (Exception e) {
			product.setPrice(0);
		}
		try {
			product.setQuantity(Integer.parseInt(req.getParameter("quantity")));
		} catch (Exception e) {
			product.setQuantity(0);
		}
		try {
			product.setStatus(Integer.parseInt(req.getParameter("status")));
		} catch (Exception e) {
			product.setStatus(1);
		}

		int categoryid = Integer.parseInt(req.getParameter("categoryid"));
		Category category = categoryService.findById(categoryid);
		product.setCategory(category);
	}

	private String handleUpload(HttpServletRequest req, String oldFileName) {
		String uploadPath = Constant.DIR;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		try {
			Part part = req.getPart("images");
			if (part != null && part.getSize() > 0) {
				String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				int index = filename.lastIndexOf(".");
				String ext = filename.substring(index + 1);
				String fname = System.currentTimeMillis() + "." + ext;
				part.write(uploadPath + "/" + fname);
				return fname;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldFileName != null ? oldFileName : Constant.DEFAULT_FILENAME;
	}
}
