package com.migros.couriertracking.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class HaversineDistanceStrategy implements DistanceCalculationStrategy {
    public final static double AVERAGE_RADIUS_OF_EARTH = 6371000;

    @Override
    public double calculateDistance(double courierLat, double courierLng, double storeLat, double storeLng) {
        double lat1Rad = Math.toRadians(courierLat);
        double lon1Rad = Math.toRadians(courierLng);
        double lat2Rad = Math.toRadians(storeLat);
        double lon2Rad = Math.toRadians(storeLng);

        double dlon = lon2Rad - lon1Rad;
        double dlat = lat2Rad - lat1Rad;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return AVERAGE_RADIUS_OF_EARTH * c;

    }
}
