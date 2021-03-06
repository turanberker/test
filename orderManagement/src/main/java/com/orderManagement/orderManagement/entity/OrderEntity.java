package com.orderManagement.orderManagement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.berker.servicebase.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orderManagement.orderManagement.constraints.OrderConstraints;
import com.orderManagement.orderManagement.data.OrderStatus;
import com.orderManagement.orderManagement.data.OrderStatusConverter;

@Entity
@Table(name = OrderConstraints.TABLE_NALE)
public class OrderEntity extends BaseEntity {

	@Column(name = "Address")
	@NotNull
	@Size(min = OrderConstraints.DETAIL_STRING_MIN, max = OrderConstraints.DETAIL_STRING_MAX)
	private String address;

	@NotNull
	@Column(name = "STATUS")
	@Convert(converter = OrderStatusConverter.class)
	private OrderStatus orderStatus=OrderStatus.NEW;
	
	@JsonIgnore
	@OneToMany(mappedBy="order", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<OrderDetailEntity> detailList;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderDetailEntity> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderDetailEntity> detailList) {
		this.detailList = detailList;
	}
	
	
	

}
