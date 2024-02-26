package com.migros.couriertracking.dao;

import com.migros.couriertracking.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
