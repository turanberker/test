package com.orderManagement.orderManagement.services;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.berker.servicebase.exceptions.NotEnoughStock;
import com.orderManagement.orderManagement.dto.NewOrderDetailModel;
import com.orderManagement.orderManagement.dto.NewOrderModel;
import com.orderManagement.orderManagement.dto.OmProductModel;
import com.orderManagement.orderManagement.dto.OmReduceFromStockModel;
import com.orderManagement.orderManagement.dto.OmReduceItemFromStockModel;
import com.orderManagement.orderManagement.entity.OrderDetailEntity;
import com.orderManagement.orderManagement.entity.OrderEntity;
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

		List<Long> productIdListe = new ArrayList<Long>();

		for (NewOrderDetailModel detail : newOrder.getOrderDetailList()) {
			productIdListe.add(detail.getProductId());
		}
		ResponseEntity<List<OmProductModel>> response = productClient.getByIdList(productIdListe);
		OmReduceFromStockModel reduceFromStockModel = new OmReduceFromStockModel();
		reduceFromStockModel.setItemList(new ArrayList());

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			List<OmProductModel> productList = response.getBody();
			OrderEntity orderEntity = new OrderEntity();

			orderEntity.setAddress(newOrder.getAddress());

			orderEntity.setDetailList(new ArrayList<OrderDetailEntity>());
			for (final OmProductModel product : productList) {
				OrderDetailEntity detail = new OrderDetailEntity();
				detail.setOrder(orderEntity);
				detail.setCategoryName(product.getCategory().getName());
				detail.setProductId(product.getId());
				detail.setProdutName(product.getName());
				detail.setUnitPrice(product.getPrice());
				Optional<NewOrderDetailModel> reqProduct = newOrder.getOrderDetailList().stream()
						.filter(e -> e.getProductId().equals(product.getId())).findFirst();

				if (reqProduct.get().getQuantity() > product.getStockQuantity()) {
					throw	 new NotEnoughStock(
							MessageFormat.format("{0} isimli ürünün miktarı yetersiz", product.getName()),
							OrderService.class.getName());
				
				} else {
					detail.setQuantity(reqProduct.get().getQuantity());
				}
				orderEntity.getDetailList().add(detail);

				OmReduceItemFromStockModel reduceItemModel = new OmReduceItemFromStockModel();
				reduceItemModel.setProductId(product.getId());
				reduceItemModel.setQuantityToReduce(reqProduct.get().getQuantity());
				reduceFromStockModel.getItemList().add(reduceItemModel);
			}
			repository.save(orderEntity);

			if (productClient.reduceFromStock(reduceFromStockModel).getStatusCode() != HttpStatus.OK) {
				throw new NotEnoughStock("YETERSİZ STOK", OrderService.class.getName());
			}

		} else {
			throw new RuntimeException("Error on Product Service");
		}

	}

}
