package com.migros.couriertracking.service;

import org.springframework.stereotype.Component;

@Component("euclideanDistanceStrategy")
public class EuclideanDistanceStrategy implements DistanceCalculationStrategy {

    @Override
    public double calculateDistance(double courierLat, double courierLng, double storeLat, double storeLng) {
        double latDifferenceInMeters = (courierLat - storeLat) * 111000;
        double lngDifferenceInMeters = (courierLng - storeLng) * 111000;

        double distance = Math.sqrt(latDifferenceInMeters * latDifferenceInMeters + lngDifferenceInMeters * lngDifferenceInMeters);

        return distance;
    }
}
