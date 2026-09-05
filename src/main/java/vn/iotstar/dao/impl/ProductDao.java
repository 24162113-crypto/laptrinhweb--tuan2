package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import vn.iotstar.configs.JpaConfig;
import vn.iotstar.dao.IProductDao;
import vn.iotstar.entity.Product;

public class ProductDao implements IProductDao {

	@Override
	public List<Product> findAll() {
		EntityManager enma = JpaConfig.getEntityManager();
		try {
			TypedQuery<Product> query = enma.createNamedQuery("Product.findAll", Product.class);
			return query.getResultList();
		} finally {
			enma.close();
		}
	}

	@Override
	public List<Product> findAll(int page, int pagesize) {
		EntityManager enma = JpaConfig.getEntityManager();
		try {
			TypedQuery<Product> query = enma.createNamedQuery("Product.findAll", Product.class);
			query.setFirstResult(page * pagesize);
			query.setMaxResults(pagesize);
			return query.getResultList();
		} finally {
			enma.close();
		}
	}

	@Override
	public List<Product> findLatest(int limit) {
		EntityManager enma = JpaConfig.getEntityManager();
		try {
			TypedQuery<Product> query = enma.createNamedQuery("Product.findLatest", Product.class);
			query.setMaxResults(limit);
			return query.getResultList();
		} finally {
			enma.close();
		}
	}

	@Override
	public Product findById(int id) {
		EntityManager enma = JpaConfig.getEntityManager();
		try {
			return enma.find(Product.class, id);
		} finally {
			enma.close();
		}
	}

	@Override
	public void insert(Product product) {
		EntityManager enma = JpaConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.persist(product);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (trans.isActive())
				trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public void update(Product product) {
		EntityManager enma = JpaConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.merge(product);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (trans.isActive())
				trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public void delete(int id) throws Exception {
		EntityManager enma = JpaConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			Product product = enma.find(Product.class, id);
			if (product != null) {
				enma.remove(product);
			} else {
				throw new Exception("Không tìm thấy sản phẩm");
			}
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (trans.isActive())
				trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public int count() {
		EntityManager enma = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT count(p) FROM Product p";
			Query query = enma.createQuery(jpql);
			return ((Long) query.getSingleResult()).intValue();
		} finally {
			enma.close();
		}
	}
}
