package com.j2ee.j2eeproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.j2ee.j2eeproject.entity.pojo.ProductImage;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {
	List<ProductImage> findByProductId(Integer productId);
}
