package com.migros.couriertracking.service;

public interface DistanceCalculationStrategy {
    double calculateDistance(double courierLat, double courierLng, double storeLat, double storeLng);
}
