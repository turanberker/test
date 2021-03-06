package com.pim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.berker.servicebase.entity.BaseEntity;
import com.pim.constraints.CategoryConstraints;
import com.sun.istack.NotNull;


@Entity
@Table(name=CategoryConstraints.TABLE_NAME)
public class CategoryEntity extends BaseEntity {

	@NotNull
	@Column(name="NAME" ,nullable=false)
	@Size(max=CategoryConstraints.NAME_MAX)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
