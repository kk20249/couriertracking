package com.migros.couriertracking.dao;

import com.migros.couriertracking.entity.CourierLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourierLogRepository extends JpaRepository<CourierLog, Long> {
    Optional<CourierLog> findFirstByCourierIdAndStoreIdOrderByTimeDesc(Long courierId, Long storeId);

    List<CourierLog> findByCourierIdOrderByTimeAsc(Long courierId);
}