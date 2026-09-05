package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.Product;
import vn.iotstar.services.IProductService;
import vn.iotstar.services.impl.ProductServiceImpl;
import vn.iotstar.utils.Constant;

@WebServlet(urlPatterns = { "/product", "/product/detail" })
public class ProductController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");

		if (url.contains("detail")) {
			String idParam = req.getParameter("id");
			if (idParam == null) {
				resp.sendRedirect(req.getContextPath() + "/product");
				return;
			}
			int id = Integer.parseInt(idParam);
			Product product = productService.findById(id);
			req.setAttribute("product", product);
			req.getRequestDispatcher("/views/product-detail.jsp").forward(req, resp);
			return;
		}

		// Danh sach san pham co phan trang, 6 san pham / trang
		int page = 1;
		String pageParam = req.getParameter("page");
		if (pageParam != null) {
			try {
				page = Integer.parseInt(pageParam);
			} catch (NumberFormatException e) {
				page = 1;
			}
		}
		if (page < 1) {
			page = 1;
		}

		int pagesize = Constant.PRODUCT_PAGE_SIZE;
		int totalPages = Math.max(productService.totalPages(pagesize), 1);
		if (page > totalPages) {
			page = totalPages;
		}

		List<Product> list = productService.findAll(page - 1, pagesize);

		req.setAttribute("listproduct", list);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);
		req.getRequestDispatcher("/views/product-list.jsp").forward(req, resp);
	}
}
