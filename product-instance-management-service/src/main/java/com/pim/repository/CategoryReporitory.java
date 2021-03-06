package com.pim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pim.entity.CategoryEntity;

@Repository
public interface CategoryReporitory extends JpaRepository<CategoryEntity, Long>{
	
	@Query("SELECT c FROM CategoryEntity c WHERE c.name like %:name%")
	List<CategoryEntity> findByNameLike(@Param("name") String name);
}
