package com.migros.couriertracking.service;

import com.migros.couriertracking.dao.StoreRepository;
import com.migros.couriertracking.entity.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllStores() {
        // Mock data
        List<Store> stores = new ArrayList<>();
        Store store = new Store();
        store.setId(1L);
        store.setLat(40.7128);
        store.setLng(-74.0060);
        stores.add(store);

        when(storeRepository.findAll()).thenReturn(stores);

        // Test
        List<Store> result = storeService.getAllStores();

        // Verify
        verify(storeRepository, times(1)).findAll();
        assertEquals(stores.size(), result.size());
        assertEquals(stores.get(0), result.get(0));
    }
}
