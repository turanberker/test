package com.pim.controller;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.berker.servicebase.configuration.BaseRestController;
import com.pim.dto.CategoryModel;
import com.pim.service.CategoryService;

@RestController
@RequestMapping(path = "/category")
public class CategoryRestController extends BaseRestController {

	@Autowired
	private CategoryService service;

	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryModel> save(@RequestParam(value = "name") String name) {
		return new ResponseEntity<CategoryModel>(service.save(name), HttpStatus.CREATED);
	}

	@GetMapping(path = "/getById/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryModel> getById(@PathVariable(value = "id") @Positive @NotNull Long id) {
		return new ResponseEntity<CategoryModel>(service.findById(id), HttpStatus.FOUND);
	}

	@GetMapping(path = "/getByName/{name}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryModel>> getById(@PathVariable(value = "name")  String name) {
		return new ResponseEntity<List<CategoryModel>>(service.findByName(name), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/delete/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteById(@PathVariable(value = "id") @Positive @NotNull Long id) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
