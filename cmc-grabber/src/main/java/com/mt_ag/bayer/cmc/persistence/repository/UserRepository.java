package com.mt_ag.bayer.cmc.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.mt_ag.bayer.cmc.persistence.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}