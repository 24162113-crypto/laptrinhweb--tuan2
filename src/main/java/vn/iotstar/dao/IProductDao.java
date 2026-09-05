package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.Product;

public interface IProductDao {

	List<Product> findAll();

	List<Product> findAll(int page, int pagesize);

	List<Product> findLatest(int limit);

	Product findById(int id);

	void insert(Product product);

	void update(Product product);

	void delete(int id) throws Exception;

	int count();
}
