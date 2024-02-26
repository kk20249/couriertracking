package com.migros.couriertracking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migros.couriertracking.dao.StoreRepository;
import com.migros.couriertracking.entity.Store;
import com.migros.couriertracking.util.CourierTrackingLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getAllStores() {
        CourierTrackingLogger.getInstance().getAllStores();
        return storeRepository.findAll();
    }

    public void insertStoresFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("stores.json");
        try (InputStream inputStream = resource.getInputStream()) {
            List<Store> stores = objectMapper.readValue(inputStream, new TypeReference<List<Store>>() {});
            storeRepository.saveAll(stores);
        }
    }
}
