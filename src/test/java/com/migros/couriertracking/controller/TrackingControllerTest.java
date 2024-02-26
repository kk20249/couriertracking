package com.migros.couriertracking.controller;

import com.migros.couriertracking.controller.request.CourierTrackingRequest;
import com.migros.couriertracking.service.CourierLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackingControllerTest {
    @Mock
    private CourierLogService courierLogService;

    @InjectMocks
    private TrackingController trackingController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReceiveCourierLocation() {
        // Mock data
        CourierTrackingRequest request = new CourierTrackingRequest();
        ResponseEntity<?> responseEntity = trackingController.receiveCourierLocation(request);

        // Test
        verify(courierLogService, times(1)).trackCourierLocation(request);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetTotalTravelDistance() {
        // Mock data
        Long courierId = 123L;
        Double totalTravelDistance = 1000.0;

        when(courierLogService.getTotalTravelDistance(courierId)).thenReturn(totalTravelDistance);

        // Test
        ResponseEntity<Double> responseEntity = trackingController.getTotalTravelDistance(courierId);

        // Verify
        verify(courierLogService, times(1)).getTotalTravelDistance(courierId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(totalTravelDistance, responseEntity.getBody());
    }
}