package com.migros.couriertracking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculationService {
    private final DistanceCalculationStrategy distanceCalculationStrategy;

    public DistanceCalculationService(@Qualifier("euclideanDistanceStrategy") DistanceCalculationStrategy distanceCalculationStrategy) {
        this.distanceCalculationStrategy = distanceCalculationStrategy;
    }


    public double calculateDistance(double courierLat, double courierLng, double storeLat, double storeLng) {
        return distanceCalculationStrategy.calculateDistance(courierLat, courierLng, storeLat, storeLng);
    }
}
