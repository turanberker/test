package com.pim.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.berker.servicebase.TrObjectMapper;
import com.berker.servicebase.configuration.BaseRestController;
import com.berker.servicebase.exceptions.DataNotFoundException;
import com.berker.servicebase.exceptions.NotEnoughStock;
import com.fasterxml.jackson.core.type.TypeReference;
import com.pim.dto.NewProductModel;
import com.pim.dto.ProductModel;
import com.pim.dto.ReduceFromStockModel;
import com.pim.dto.ReduceItemFromStockModel;
import com.pim.dto.UpdateStockModel;
import com.pim.entity.CategoryEntity;
import com.pim.entity.ProductEntity;
import com.pim.repository.ProductRepository;

@Service
public class ProductService extends BaseRestController {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private EntityManager entityManager;

	public ProductModel save(@Valid NewProductModel newProduct) {
		ProductEntity productEntity = new ProductEntity();
		productEntity.setName(newProduct.getName());
		productEntity.setPrice(newProduct.getPrice());
		productEntity.setStockQuantity(newProduct.getStockQuantity());
		productEntity.setCategory(entityManager.getReference(CategoryEntity.class, newProduct.getCategoryId()));
		repository.saveAndFlush(productEntity);

		return TrObjectMapper.mapper.convertValue(productEntity, ProductModel.class);
	}

	@Transactional(readOnly = true)
	public List<ProductModel> getByCategoryId(@NotNull @Positive Long categoryId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
		Root<ProductEntity> user = query.from(ProductEntity.class);

		Path<CategoryEntity> kategoryPath = user.get("category");

		List<Predicate> predicates = new ArrayList();

		predicates.add(cb.equal(kategoryPath.get("id"), categoryId));

		query.select(user).where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

		List<ProductEntity> productList = entityManager.createQuery(query).getResultList();
		return TrObjectMapper.mapper.convertValue(productList, new TypeReference<List<ProductModel>>() {
		});
	}

	public List<ProductModel> getByIdList(@NotEmpty List<Long> productIdList) {
		List<ProductEntity> productList = repository.findAllById(productIdList);
		return TrObjectMapper.mapper.convertValue(productList, new TypeReference<List<ProductModel>>() {
		});

	}

	public ProductModel updateStockQuantity(@Valid UpdateStockModel stokGuncelleModel) {
		Optional<ProductEntity> productOp = repository.findById(stokGuncelleModel.getProductId());

		if (productOp.isPresent()) {
			productOp.get().setStockQuantity(stokGuncelleModel.getStockQuantity());
			ProductEntity updatedProduct = repository.save(productOp.get());
			return TrObjectMapper.mapper.convertValue(updatedProduct, ProductModel.class);
		} else {
			throw new DataNotFoundException(ProductEntity.class.getSimpleName(),
					String.format("Product with id=%d is not found", stokGuncelleModel.getProductId()));
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void reduceFromStock(@Valid ReduceFromStockModel reduceFromStock) {

		List<Long> productIdList = reduceFromStock.getItemList().stream().map(e -> e.getProductId())
				.collect(Collectors.toList());
		List<ProductEntity> productEntityList = repository.findAllById(productIdList);

		if (productEntityList.size() != productIdList.size()) {
			throw new DataNotFoundException(ProductEntity.class.getName(),
					productIdList.toString() + " ID Lİ KAYITLAR BULUNAMADI");
		}

		List<ProductEntity> entitytoUpdate = new ArrayList<>();
		for (ReduceItemFromStockModel fromStockModel : reduceFromStock.getItemList()) {
			Optional<ProductEntity> op = productEntityList.stream()
					.filter(e -> e.getId().equals(fromStockModel.getProductId())).findFirst();
			if (op.isPresent()) {
				ProductEntity entity = op.get();
				if (entity.getStockQuantity() < fromStockModel.getQuantityToReduce()) {
					throw new NotEnoughStock(
							MessageFormat.format("{0} id li ürünün stoğu yetersiz", fromStockModel.getProductId()),ProductEntity.class.getName());
				} else {
					entity.setStockQuantity(entity.getStockQuantity() - fromStockModel.getQuantityToReduce());
					entitytoUpdate.add(entity);
				}

			} else {
				throw new DataNotFoundException(ProductEntity.class.getName(),
						fromStockModel.getProductId() + " ID Lİ KAYITLAR BULUNAMADI");
			}
		}
		repository.saveAllAndFlush(entitytoUpdate);
	}
}