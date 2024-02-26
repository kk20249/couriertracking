package com.migros.couriertracking.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HaversineDistanceStrategyTest {

    @Test
    public void testCalculateDistance() {
        // Mock data
        HaversineDistanceStrategy haversineDistanceStrategy = new HaversineDistanceStrategy();

        double courierLat = 40.7128;
        double courierLng = 29.1244229;


        double storeLat = 41.9923307;
        double storeLng = 29.1244229;

        double expectedDistance = 142277;

        // Test
        double actualDistance = haversineDistanceStrategy.calculateDistance(courierLat, courierLng, storeLat, storeLng);

        // Verify
        assertEquals(expectedDistance, actualDistance, 1000.0);
    }

}