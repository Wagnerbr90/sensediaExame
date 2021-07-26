package com.beerhouse.resource;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.beerhouse.model.Beer;

@SpringBootTest
public class BeerResourceTest {

	@Autowired BeerResource beerResource;
	
	@Test
	public void listBeer() {
		assertTrue(beerResource.findAll().getStatusCode().is2xxSuccessful());
	}
	
	@Test
	public void saveBeer() {
		Beer beer = new Beer();
		beer.setAlcohol_content("5.6%");
		beer.setCategory("Bebida Alcoólica");
		beer.setIngredients("Cevada");
		beer.setName("Skol Sensations");
		beer.setPrice(4.69);
		assertTrue(beerResource.saveBeer(beer, null).getStatusCode().is2xxSuccessful());
	}
	
	@Test
	public void findOne() {
		ResponseEntity<Beer> status = beerResource.findOne(1);
		assertTrue(status.getStatusCode().is2xxSuccessful() || status.equals(ResponseEntity.notFound().build()));
	}

	@Test
	public void updateBeer() {
		Beer beer = new Beer();
		beer.setAlcohol_content("5.6%");
		beer.setCategory("Bebida Alcoólica");
		beer.setIngredients("Cevada");
		beer.setName("Skol Sensations");
		beer.setPrice(4.69);
		ResponseEntity<Beer> status = beerResource.updateBeer(1,beer);
		assertTrue(status.getStatusCode().is2xxSuccessful()
				 || status.equals(ResponseEntity.notFound().build()));
	}

	@Test	
	public void partialUpdateBeer() {
		Map<Object, Object> map = new HashMap<>();
		map.put("alcohol_content", "3.6%");
		map.put("name", "Skol Sensations");
		map.put("price", "3.75");
		ResponseEntity<Beer> status = beerResource.partialUpdateBeer(1, map);
		assertTrue(status.getStatusCode().is2xxSuccessful()
				|| status.equals(ResponseEntity.notFound().build()));
	}
	
	@Test
	public void deleteOne() {
		ResponseEntity<Beer> status = beerResource.findOne(1);
		assertTrue(status.getStatusCode().is2xxSuccessful() || status.equals(ResponseEntity.notFound().build()));
	}
}
