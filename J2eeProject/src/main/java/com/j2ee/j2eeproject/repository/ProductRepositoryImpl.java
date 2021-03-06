package com.j2ee.j2eeproject.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.j2ee.j2eeproject.entity.pojo.Product;

@Repository
@Transactional(readOnly = true)
public class ProductRepositoryImpl implements ProductRepositoryCustom {
	@PersistenceContext
    EntityManager entityManager;
	
	@Override
	public List<Product> selectTopProductByCatalogId(int limitNumber, int catalog_id) {
		Query query = entityManager.createNativeQuery("SELECT * FROM PRODUCT WHERE CATALOG_ID = ?" + " LIMIT ?;", Product.class);
        query.setParameter(1, catalog_id);
        query.setParameter(2, limitNumber);
        return query.getResultList();
	}
	
	@Override
	public List<Product> selectLatestProduct(int limitedNumber) {
		Query query = entityManager.createNativeQuery("SELECT * FROM PRODUCT ORDER BY CREATED_DATE DESC LIMIT ?;", Product.class);
        query.setParameter(1, limitedNumber);
    
        return query.getResultList();
	}
	
	@Override
	public List<Product> selectTopRating(int limitedNumber) {
		Query query = entityManager.createNativeQuery("SELECT * FROM PRODUCT ORDER BY RATING DESC LIMIT ?;", Product.class);
        query.setParameter(1, limitedNumber);
    
        return query.getResultList();
	}

	@Override
	public Iterable<Product> selectByCatalogId(int id) {
		Query query = entityManager.createNativeQuery("SELECT *FROM PRODUCT WHERE CATALOG_ID = ?;", Product.class);
        query.setParameter(1, id);
    
        return query.getResultList();
	}

}
