package com.orderManagement.orderManagement.services;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.berker.servicebase.TrObjectMapper;
import com.berker.servicebase.exceptions.DataNotFoundException;
import com.berker.servicebase.exceptions.NotEnoughStock;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orderManagement.orderManagement.data.OrderStatus;
import com.orderManagement.orderManagement.dto.AddNewItemModel;
import com.orderManagement.orderManagement.dto.ChangeAddressModel;
import com.orderManagement.orderManagement.dto.NewOrderDetailModel;
import com.orderManagement.orderManagement.dto.NewOrderModel;
import com.orderManagement.orderManagement.dto.OmProductModel;
import com.orderManagement.orderManagement.dto.OmStockItemMovementModel;
import com.orderManagement.orderManagement.dto.OmStockMovementModel;
import com.orderManagement.orderManagement.dto.OrderModel;
import com.orderManagement.orderManagement.entity.OrderDetailEntity;
import com.orderManagement.orderManagement.entity.OrderEntity;
import com.orderManagement.orderManagement.exceptions.OrderCanNotEdittedException;
import com.orderManagement.orderManagement.feigns.ProductFeignClient;
import com.orderManagement.orderManagement.repositories.OrderDetailRepository;
import com.orderManagement.orderManagement.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private OrderDetailRepository detailRepository;

	@Autowired
	private ProductFeignClient productClient;

	@Transactional(rollbackFor = Exception.class)
	public void save(@Valid NewOrderModel newOrder) {

		List<Long> productIdListe = newOrder.getOrderDetailList().stream().map(e -> e.getProductId())
				.collect(Collectors.toList());

		ResponseEntity<List<OmProductModel>> response = productClient.getByIdList(productIdListe);
		OmStockMovementModel reduceFromStockModel = new OmStockMovementModel();
		reduceFromStockModel.setItemList(new ArrayList<>());

		if (response.getStatusCode().equals(HttpStatus.OK)) {

			if (response.getBody().size() != productIdListe.size()) {
				throw new RuntimeException("Some Items does not exists");
			}
			List<OmProductModel> productList = response.getBody();
			OrderEntity orderEntity = new OrderEntity();

			orderEntity.setAddress(newOrder.getAddress());

			orderEntity.setDetailList(new ArrayList<OrderDetailEntity>());
			for (final OmProductModel product : productList) {
				OrderDetailEntity detail = new OrderDetailEntity(product);
				detail.setOrder(orderEntity);
				Optional<NewOrderDetailModel> reqProduct = newOrder.getOrderDetailList().stream()
						.filter(e -> e.getProductId().equals(product.getId())).findFirst();

				if (reqProduct.get().getQuantity() > product.getStockQuantity()) {
					throw new NotEnoughStock(
							MessageFormat.format("{0} isimli ??r??n??n miktar?? yetersiz", product.getName()),
							OrderService.class.getName());

				} else {
					detail.setQuantity(reqProduct.get().getQuantity());
				}
				orderEntity.getDetailList().add(detail);

				reduceFromStockModel.getItemList()
						.add(new OmStockItemMovementModel(product.getId(), reqProduct.get().getQuantity()));
			}
			repository.save(orderEntity);

			if (productClient.reduceFromStock(reduceFromStockModel).getStatusCode() != HttpStatus.OK) {
				throw new NotEnoughStock("YETERS??Z STOK", OrderService.class.getName());
			}

		} else {
			throw new RuntimeException("Error on Product Service");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void cancelOrder(@NotNull @Positive Long orderId) {

		OrderEntity orderToCancel = findOrder(orderId);
		orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
		repository.save(orderToCancel);

		List<OrderDetailEntity> detailList = detailRepository.getByOrderId(orderId);

		List<OmStockItemMovementModel> itemtoMoveList = detailList.stream()
				.map(e -> new OmStockItemMovementModel(e.getId(), e.getQuantity())).collect(Collectors.toList());
		OmStockMovementModel moveModel = new OmStockMovementModel();
		moveModel.setItemList(itemtoMoveList);

		HttpStatus result = productClient.addToStock(moveModel).getStatusCode();
		if (result != HttpStatus.OK) {
			throw new RuntimeException("Error Occured on Product Service");
		}

	}

	private OrderEntity findOrder(Long orderId) {
		Optional<OrderEntity> order = repository.findById(orderId);
		if (order.isPresent()) {
			return order.get();
		} else {
			throw new DataNotFoundException(OrderService.class.getName(),
					MessageFormat.format("Order with id={0} is not found", orderId));
		}
	}

	private void isOrderEdittable(OrderEntity order) {

		if (!OrderStatus.NEW.equals(order.getOrderStatus())) {
			throw new OrderCanNotEdittedException(order.getId(), order.getOrderStatus(), OrderService.class.getName());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void changeAddress(@Valid ChangeAddressModel changeAddressModel) {
		OrderEntity order = findOrder(changeAddressModel.getOrderId());
		isOrderEdittable(order);
		order.setAddress(changeAddressModel.getNewAddress());
		repository.save(order);

	}

	@Transactional(rollbackFor = Exception.class)
	public void addItem(@Valid AddNewItemModel newItem) {
		OrderEntity order = findOrder(newItem.getOrderId());
		isOrderEdittable(order);

		OrderDetailEntity detail = detailRepository.findByOrderIdAndProductId(newItem.getOrderId(),
				newItem.getNewItem().getProductId());

		ResponseEntity<OmProductModel> response = productClient.getById(newItem.getNewItem().getProductId());
		OmProductModel productModel;
		// ProductCheck
		if (response.getStatusCode() == HttpStatus.OK) {
			productModel = response.getBody();
		} else {
			throw new DataNotFoundException(OrderService.class.getName(),
					MessageFormat.format("Product with ID={0} is not exists", newItem.getNewItem().getProductId()));
		}

		// Stok Check
		if (productModel.getStockQuantity() < newItem.getNewItem().getQuantity()) {
			throw new NotEnoughStock(MessageFormat.format("{0} isimli ??r??n??n miktar?? yetersiz", productModel.getName()),
					OrderService.class.getName());
		}

		// if this product is added before
		if (detail == null) {
			if (response.getStatusCode() == HttpStatus.OK) {
				detail = new OrderDetailEntity(productModel);
				detail.setQuantity(newItem.getNewItem().getQuantity());
			}
		} else {
			detail.setQuantity(detail.getQuantity() + newItem.getNewItem().getQuantity());
		}

		detailRepository.save(detail);
		OmStockMovementModel reduceFromStockModel = new OmStockMovementModel();
		reduceFromStockModel.setItemList(Collections.singletonList(
				new OmStockItemMovementModel(newItem.getNewItem().getProductId(), newItem.getNewItem().getQuantity())));

		if (productClient.reduceFromStock(reduceFromStockModel).getStatusCode() != HttpStatus.OK) {
			throw new NotEnoughStock("YETERS??Z STOK", OrderService.class.getName());
		}
	}
	

	public List<OrderModel> getAllOrders(@NotNull @PositiveOrZero int pageIndex){
		
		//TODO bir pageable obje yap??lacak ve ona case edilecek
		PageRequest page = PageRequest.of(pageIndex, 5);
		Page<OrderEntity> pageEntity=repository.findAll(page);
		return TrObjectMapper.mapper.convertValue(pageEntity.getContent(), new TypeReference<List<OrderModel>>() {
		});
	}
}
