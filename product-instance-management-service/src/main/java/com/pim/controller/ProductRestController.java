package com.pim.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pim.dto.NewProductModel;
import com.pim.dto.ProductModel;
import com.pim.dto.ReduceFromStockModel;
import com.pim.service.ProductService;

@RestController
@RequestMapping(path = "/product")
public class ProductRestController {

	@Autowired
	private ProductService service;

	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductModel> save(@RequestBody NewProductModel newProduct) {
		return new ResponseEntity<ProductModel>(service.save(newProduct), HttpStatus.CREATED);
	}

	@GetMapping(path = "/getByCategoryId/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductModel>> getById(
			@PathVariable(value = "categoryId") @Positive @NotNull Long categoryId) {
		return new ResponseEntity<List<ProductModel>>(service.getByCategoryId(categoryId), HttpStatus.OK);
	}

	@GetMapping(path = "/getByIdList/{list}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductModel>> getByIdList(@PathVariable(value = "list") List<Long> productIdList) {
		return new ResponseEntity<List<ProductModel>>(service.getByIdList(productIdList), HttpStatus.OK);
	}
	
	@PostMapping(path="/reduceFromStock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity reduceFromStock(@RequestBody @Valid ReduceFromStockModel reduceFromStock) {
		service.reduceFromStock(reduceFromStock);
		return new ResponseEntity<List<ProductModel>>(HttpStatus.OK);
	}
}
