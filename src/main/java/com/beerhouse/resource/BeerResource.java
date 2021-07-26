package com.beerhouse.resource;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.beerhouse.model.Beer;
import com.beerhouse.repository.BeerRepository;


@RestController
@RequestMapping("/beers")
public class BeerResource {

	@Autowired
	private BeerRepository beerRepository;
	
	@GetMapping
	public ResponseEntity<List<Beer>> findAll() {
		List<Beer> beerList = beerRepository.findAll();
		return ResponseEntity.ok(beerList);
	}
	
	@PostMapping
	public ResponseEntity<Beer> saveBeer(@Valid @RequestBody Beer beer, HttpServletResponse response) {
		Beer newBeer = beerRepository.save(beer);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newBeer.getId()).toUri();
		
		return ResponseEntity.created(uri).body(newBeer);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Beer> findOne(@PathVariable Integer id) {
		Optional<Beer> beer = beerRepository.findById(id);
		return beer.isPresent() ? ResponseEntity.ok(beer.get()) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Beer> updateBeer(@PathVariable Integer id, @Valid @RequestBody Beer beer) {
		Optional<Beer> beer2 = beerRepository.findById(id);
		if (beer2.isPresent()) {
			BeanUtils.copyProperties(beer, beer2.get(), "id");
			beerRepository.save(beer2.get());
			return ResponseEntity.ok(beer2.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Beer> partialUpdateBeer(@PathVariable Integer id, @Valid @RequestBody Map<Object, Object> fields) {
		Optional<Beer> beer2 = beerRepository.findById(id);
		if (beer2.isPresent()) {
			fields.forEach((key,value) -> {
				Field field = ReflectionUtils.findField(Beer.class, key.toString());
				field.setAccessible(true);
				ReflectionUtils.setField(field, beer2.get(), field.getType().isPrimitive() 
						? Double.valueOf(value.toString()) 
						: value);
			});
			beerRepository.save(beer2.get());
			return ResponseEntity.ok(beer2.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable Integer id) {
		Optional<Beer> beer = beerRepository.findById(id);
		if (beer.isPresent()) {
			beerRepository.deleteById(id);
			return ResponseEntity.ok(beer);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
