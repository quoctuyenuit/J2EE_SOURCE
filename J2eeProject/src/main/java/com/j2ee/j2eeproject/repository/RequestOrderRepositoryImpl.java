package com.j2ee.j2eeproject.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.j2ee.j2eeproject.entity.pojo.Product;
@Repository
@Transactional(readOnly = false)
public class RequestOrderRepositoryImpl implements RequestOrderRepositoryCustom{
	@PersistenceContext
    EntityManager entityManager;
	
	@Override
	public int saveRequestOrder(String userId) {
		// TODO Auto-generated method stub
		Query query = entityManager.createNativeQuery("INSERT INTO REQUEST_ORDER(USER_ID, CREATED) VALUE(?, CURDATE());");
        query.setParameter(1, userId);
        Query getQuery  = entityManager.createNativeQuery("SELECT MAX(ID) FROM REQUEST_ORDER;");
        
        try {
        	query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        List<Integer> resultsIntegers = getQuery.getResultList();
        return resultsIntegers.get(0);
	}

}
