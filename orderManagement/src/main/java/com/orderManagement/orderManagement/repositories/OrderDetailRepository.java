package com.orderManagement.orderManagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orderManagement.orderManagement.entity.OrderDetailEntity;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long>{

	List<OrderDetailEntity> getByOrderId(Long orderId);
	
	@Query("Select d from OrderDetailEntity d where d.order.id=:orderId and d.productId=:productId")
	OrderDetailEntity findByOrderIdAndProductId(@Param("orderId") Long orderId,@Param("productId") Long productId);
}
