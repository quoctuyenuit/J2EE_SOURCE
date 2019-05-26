package com.j2ee.j2eeproject.repository;

import java.util.List;

import com.j2ee.j2eeproject.entity.pojo.Product;

public interface ProductRepositoryCustom {
	List<Product> selectTopProductByCatalogId(int limitNumber, int catalog_id);
}
