package com.j2ee.j2eeproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.j2ee.j2eeproject.entity.ImageSample;

public interface ImageSampleRepository extends CrudRepository<ImageSample, Integer> {
	List<ImageSample> findByProductIdContaining(Integer product_id);
}
