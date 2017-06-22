package com.myshop.service;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.myshop.domain.Product;
import com.myshop.repository.ProductRepository;

@Stateless
public class ProductResourceImpl implements ProductResource {

	@Inject
	private ProductRepository repo;
	
	public Response addProduct(Product p) {
		repo.save(p);
		return Response.created(URI.create("products/" + p.getId())).build();
	}

	public Product getProduct(Long id) {
		Product p = repo.findById(id);
		if (p == null) {
			throw new NotFoundException();
		}
		return p;
	}

	public List<Product> getProducts() {
		List<Product> list = repo.findAll();
		if (list == null || list.size() == 0) {
			list = null;
		}
		return list;
	}

	public void updateProduct(Long id, Product p) {
		if (id != p.getId()) {
			throw new BadRequestException();
		}
		if (repo.findById(id) == null) {
			throw new NotFoundException();
		}
		repo.update(p);
	}

	public void deleteProduct(Long id) {
		Product p = repo.findById(id);
		if (p == null) {
			throw new NotFoundException();
		}
		repo.delete(p);
	}
}
