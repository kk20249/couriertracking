package com.migros.couriertracking.service;

import com.migros.couriertracking.dao.CourierLogRepository;
import com.migros.couriertracking.entity.CourierLog;
import com.migros.couriertracking.controller.request.CourierTrackingRequest;
import com.migros.couriertracking.entity.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CourierLogServiceTest {

    @Mock
    private CourierLogRepository courierLogRepository;

    @Mock
    private StoreService storeService;

    @Mock
    private DistanceCalculationService distanceCalculationService;

    @InjectMocks
    private CourierLogService courierLogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTrackCourierLocation() {
        // Mock data
        CourierTrackingRequest request = new CourierTrackingRequest();
        request.setCourierId(1L);
        request.setLat(40.7128);
        request.setLng(-74.0060);
        request.setTime(LocalDateTime.now());

        Store store1 = new Store();
        store1.setId(1L);
        store1.setLat(40.7128);
        store1.setLng(-74.0060);
        Store store2 = new Store();
        store2.setId(2L);
        store2.setLat(40.741895);
        store2.setLng(-73.989308);
        List<Store> stores = new ArrayList<>();
        stores.add(store1);
        stores.add(store2);

        when(storeService.getAllStores()).thenReturn(stores);
        when(distanceCalculationService.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(50.0);

        // Test
        courierLogService.trackCourierLocation(request);

        // Verify
        verify(courierLogRepository, times(1)).save(any(CourierLog.class));
    }

    @Test
    public void testTrackCourierLocation_NoStores() {
        // Mock data
        CourierTrackingRequest request = new CourierTrackingRequest();
        request.setCourierId(1L);
        request.setLat(40.7128);
        request.setLng(-74.0060);
        request.setTime(LocalDateTime.now());

        List<Store> stores = new ArrayList<>();

        when(storeService.getAllStores()).thenReturn(stores);

        // Test
        courierLogService.trackCourierLocation(request);

        // Verify
        verifyNoInteractions(courierLogRepository);
    }

    @Test
    public void testTrackCourierLocation_NoNearbyStores() {
        // Mock data
        CourierTrackingRequest request = new CourierTrackingRequest();
        request.setCourierId(1L);
        request.setLat(40.7128);
        request.setLng(-74.0060);
        request.setTime(LocalDateTime.now());

        Store store = new Store();
        store.setId(1L);
        store.setLat(40.730610);
        store.setLng(-73.935242); // Single store
        List<Store> stores = new ArrayList<>();
        stores.add(store);

        when(storeService.getAllStores()).thenReturn(stores);
        when(distanceCalculationService.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(200.0); // Distance greater than 100 meters

        // Test
        courierLogService.trackCourierLocation(request);

        // Verify
        verifyNoInteractions(courierLogRepository);
    }

    @Test
    public void testGetTotalTravelDistance() {
        // Mock data
        Long courierId = 1L;
        CourierLog log1 = new CourierLog();
        log1.setId(1L);
        log1.setCourierId(courierId);
        log1.setLat(40.7128);
        log1.setLng(-74.0060);
        log1.setTime(LocalDateTime.now());
        CourierLog log2 = new CourierLog();
        log2.setId(2L);
        log2.setCourierId(courierId);
        log2.setLat(40.730610);
        log2.setLng(-73.935242);
        log2.setTime(LocalDateTime.now().plusHours(1));
        List<CourierLog> courierLogs = new ArrayList<>();
        courierLogs.add(log1);
        courierLogs.add(log2);

        when(courierLogRepository.findByCourierIdOrderByTimeAsc(courierId)).thenReturn(courierLogs);
        when(distanceCalculationService.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(100.0); // Distance is 100 meters for simplicity

        // Test
        double totalDistance = courierLogService.getTotalTravelDistance(courierId);

        // Verify
        assertEquals(100.0, totalDistance);
    }

}
