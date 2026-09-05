package vn.iotstar.services.impl;

import java.util.List;

import vn.iotstar.dao.IProductDao;
import vn.iotstar.dao.impl.ProductDao;
import vn.iotstar.entity.Product;
import vn.iotstar.services.IProductService;

public class ProductServiceImpl implements IProductService {

	private IProductDao productDao = new ProductDao();

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}

	@Override
	public List<Product> findAll(int page, int pagesize) {
		return productDao.findAll(page, pagesize);
	}

	@Override
	public List<Product> findLatest(int limit) {
		return productDao.findLatest(limit);
	}

	@Override
	public Product findById(int id) {
		return productDao.findById(id);
	}

	@Override
	public void insert(Product product) {
		productDao.insert(product);
	}

	@Override
	public void update(Product product) {
		Product old = productDao.findById(product.getProductid());
		if (old != null) {
			productDao.update(product);
		}
	}

	@Override
	public void delete(int id) throws Exception {
		productDao.delete(id);
	}

	@Override
	public int count() {
		return productDao.count();
	}

	@Override
	public int totalPages(int pagesize) {
		int total = count();
		return (int) Math.ceil((double) total / pagesize);
	}
}
