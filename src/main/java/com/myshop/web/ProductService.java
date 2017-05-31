package com.myshop.web;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.myshop.domain.Product;
import com.myshop.repository.ProductRepository;

@Stateless
@Path("/products")
public class ProductService {
	@Inject
	private ProductRepository repo;
	
	@GET
	@Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Product getProduct(@PathParam("id") Long id) {
		Product p = repo.findById(id);
        if (p == null) {
            throw new NotFoundException();
        }
		return p;
	}

	@GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Product> getProducts() {
		List<Product> list = repo.findAll();
        if (list == null || list.size() == 0) {
            list = null;
        }
		return list;
	}

	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response addProduct(Product p) {
		repo.save(p);
		return Response.created(URI.create("products/" + p.getId())).build();
	}

	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void updateProduct(@PathParam("id") Long id, Product p) {
		if (id != p.getId()) {
            throw new BadRequestException();
		}
        if (repo.findById(id) == null) {
            throw new NotFoundException();
        }
        repo.update(p);
	}

	@DELETE
	@Path("{id}")
	public void deleteProduct(@PathParam("id") Long id) {
		Product p = repo.findById(id);
        if (p == null) {
            throw new NotFoundException();
        }
        repo.delete(p);
	}
}
