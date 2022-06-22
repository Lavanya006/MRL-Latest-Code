package com.mt_ag.bayer.cmc.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.mt_ag.bayer.cmc.persistence.entity.Unit;

public interface UnitRepository extends CrudRepository<Unit, Long> {
	List<Unit> findByNameIgnoreCaseEquals(String name);
}