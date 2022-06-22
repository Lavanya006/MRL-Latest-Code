package com.mt_ag.bayer.cmc.persistence.repository;


import java.util.List;

import com.mt_ag.bayer.cmc.persistence.entity.MrlProduct;
import org.springframework.data.repository.CrudRepository;


public interface MrlProductRepository extends CrudRepository<MrlProduct, Long> {
	
	MrlProduct findByProductName(String name);
	List<MrlProduct> save(List<MrlProduct> list);
}
