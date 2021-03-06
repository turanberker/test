package com.orderManagement.orderManagement.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.berker.servicebase.TrObjectMapper;
import com.berker.servicebase.configuration.BaseRestController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orderManagement.orderManagement.dto.AddNewItemModel;
import com.orderManagement.orderManagement.dto.ChangeAddressModel;
import com.orderManagement.orderManagement.dto.NewOrderModel;
import com.orderManagement.orderManagement.dto.OrderModel;
import com.orderManagement.orderManagement.entity.OrderEntity;
import com.orderManagement.orderManagement.services.OrderService;

@RestController
@RequestMapping(path = "/order")
public class OrderRestController extends BaseRestController {

	@Autowired
	private OrderService service;

	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(@RequestBody @Valid NewOrderModel newOrder) {

		service.save(newOrder);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping(path = "/cancelOrder", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cancelOrder(@RequestParam("orderId") @NotNull @Positive Long orderId) {
		service.cancelOrder(orderId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "/changeAddress", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> changeAddress(@RequestBody @Valid ChangeAddressModel changeAddressModel) {
		service.changeAddress(changeAddressModel);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "/addItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addItem(@RequestBody @Valid AddNewItemModel newItem) {
		service.addItem(newItem);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/findByPageIndex/{pageIndex}")
	public ResponseEntity<List<OrderModel>> getAllOrders(
			@PathVariable("pageIndex") @NotNull @PositiveOrZero int pageIndex) {

		return new ResponseEntity<>(service.getAllOrders(pageIndex), HttpStatus.OK);
	}
}
