package com.myshop.service;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.myshop.domain.Product;
import com.myshop.repository.ProductRepository;
import com.myshop.service.AppConfig;
import com.myshop.service.ProductResource;

@RunWith(Arquillian.class)
public class ProductResourceTest {

	private static final String PRODUCTS_URL   = "/products";
	private static final String PRODUCTS_1_URL = "/products/1";
	private static final String PRODUCTS_2_URL = "/products/2";
	
	@Deployment(testable=false)
	public static Archive<?> createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class, "myshop.war")
				.addClasses(AppConfig.class, ProductResource.class,
						ProductResourceImpl.class, ProductRepository.class,
						Product.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/persistence.xml");

		System.out.println(war.toString(true));
		return war;
	}

	@Test
	public void shouldAddNewProduct(@ArquillianResteasyResource(PRODUCTS_URL) WebTarget target) {
		Product p = new Product("iphone 7", "apple", 20.0f);
		Response response = target.request().post(Entity.json(p));
		// Successful HTTP response: 201, “Created”
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		System.out.println("Location: " + response.getLocation());
		response.close();
	}

	@Test
	public void shouldGetExistingProduct(@ArquillianResteasyResource(PRODUCTS_1_URL) WebTarget target) {
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		// Successful HTTP response: 200, “OK”
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		System.out.println("Response body: " + response.readEntity(String.class));
		response.close();
	}

	@Test
	public void shouldGetAllExistingProducts(@ArquillianResteasyResource(PRODUCTS_URL) WebTarget target) {
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		// Successful HTTP response: 200, “OK”
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		System.out.println("Response body: " + response.readEntity(String.class));
		response.close();
	}

	@Test
	public void shouldUpdateExistingProduct(@ArquillianResteasyResource(PRODUCTS_1_URL) WebTarget target) {
		// test for xml support
		Product p = target.request(MediaType.APPLICATION_XML).get(Product.class);
		//update product details
		p.setName("mate 9");
		p.setDescription("huawei");
		p.setPrice(10.0f);

		Response response = target.request().put(Entity.xml(p));
		// Successful HTTP response: 204, “No Content”
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
		response.close();
		String body = target.request(MediaType.APPLICATION_XML).get(String.class);
		System.out.println("Response body: " + body);
	}
	
	@Test
	public void shouldDeleteExistingProduct(@ArquillianResteasyResource(PRODUCTS_2_URL) WebTarget target) {
		Response response = target.request().delete();
		// Successful HTTP response: 204, “No Content”
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

		response = target.request(MediaType.APPLICATION_JSON).get();
		// Error HTTP response: 404, “Not Found”
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		response.close();
	}
}
