package com.myshop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.myshop.domain.Product;

public class ProductRepository {
	@PersistenceContext
	private EntityManager em;

	public void save(Product p) {
		em.persist(p);
	}

	public void update(Product p) {
		em.merge(p);
	}

	public void delete(Product p) {
		em.remove(p);
	}

	public Product findById(Long id) {
		return em.find(Product.class, id);
	}

	public List<Product> findAll() {
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
		return query.getResultList();
	}

}
