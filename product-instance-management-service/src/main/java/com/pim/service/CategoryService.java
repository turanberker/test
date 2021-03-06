package com.pim.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berker.servicebase.TrObjectMapper;
import com.berker.servicebase.exceptions.DataNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.pim.dto.CategoryModel;
import com.pim.entity.CategoryEntity;
import com.pim.repository.CategoryReporitory;
import com.sun.istack.NotNull;

@Service
public class CategoryService {

	@Autowired
	private CategoryReporitory repository;

	public CategoryModel save(String name) {
		CategoryEntity entity = new CategoryEntity();
		entity.setName(name);
		repository.save(entity);
		return TrObjectMapper.mapper.convertValue(entity, CategoryModel.class);

	}

	public CategoryModel findById(@NotNull Long id) {
		Optional<CategoryEntity> entity = repository.findById(id);

		if (entity.isPresent()) {
			return TrObjectMapper.mapper.convertValue(entity.get(), CategoryModel.class);
		} else {
			throw new DataNotFoundException(CategoryEntity.class.getSimpleName(),String.format("Category with id=%d is not found", id));
		}
	}

	public List<CategoryModel> findByName(String name) {
		List<CategoryEntity> list = repository.findByNameLike(name);
		return TrObjectMapper.mapper.convertValue(list, new TypeReference<List<CategoryModel>>() {
		});
	}

}
