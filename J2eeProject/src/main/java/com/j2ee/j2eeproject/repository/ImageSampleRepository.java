package com.j2ee.j2eeproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.j2ee.j2eeproject.entity.ImageSample;

@Repository
public interface ImageSampleRepository extends CrudRepository<ImageSample, Integer> {
	List<ImageSample> findByProductIdContaining(Integer product_id);
}
