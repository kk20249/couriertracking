package com.migros.couriertracking.service;

import com.migros.couriertracking.dao.CourierLogRepository;
import com.migros.couriertracking.entity.CourierLog;
import com.migros.couriertracking.controller.request.CourierTrackingRequest;
import com.migros.couriertracking.entity.Store;
import com.migros.couriertracking.util.CourierTrackingLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourierLogService {

    @Autowired
    private CourierLogRepository courierLogRepository;
    @Autowired
    private StoreService storeService;
    @Autowired
    private DistanceCalculationService distanceCalculationService;

    private final CourierTrackingLogger courierTrackingLogger = CourierTrackingLogger.getInstance();

    public void trackCourierLocation(CourierTrackingRequest request) {
        List<Store> stores = storeService.getAllStores();

        for (Store store : stores) {
            double distanceToStore = distanceCalculationService.calculateDistance(request.getLat(), request.getLng(), store.getLat(), store.getLng());

            if (distanceToStore < 100) {
                courierTrackingLogger.logCourierEntry(request.getCourierId(), store.getId());
                if (!isCourierRecentlyEntered(request.getCourierId(), store.getId(), request.getTime())) {
                    saveCourierLog(request, store.getId());
                    return;
                }
                courierTrackingLogger.logCourierEnteredOver1Minute(request.getCourierId(), store.getId());
            }
            courierTrackingLogger.logCourierNotEntered(request.getCourierId(), store.getId());
        }
    }


    public Double getTotalTravelDistance(Long courierId) {
        List<CourierLog> courierLogs = courierLogRepository.findByCourierIdOrderByTimeAsc(courierId);
        double totalDistance = 0.0;

        for (int i = 0; i < courierLogs.size() - 1; i++) {
            CourierLog currentLog = courierLogs.get(i);
            CourierLog nextLog = courierLogs.get(i + 1);

            double distance = distanceCalculationService.calculateDistance(currentLog.getLat(), currentLog.getLng(),
                    nextLog.getLat(), nextLog.getLng());
            totalDistance += distance;
        }

        return totalDistance;
    }

    private boolean isCourierRecentlyEntered(Long courierId, Long storeId, LocalDateTime logTime) {
        Optional<CourierLog> lastLogEntryOptional = courierLogRepository.findFirstByCourierIdAndStoreIdOrderByTimeDesc(courierId, storeId);

        if (lastLogEntryOptional.isEmpty()) {
            return false;
        }

        CourierLog lastLogEntry = lastLogEntryOptional.get();
        LocalDateTime lastEntryTime = lastLogEntry.getTime();

        LocalDateTime oneMinuteAfterLastEntry = lastEntryTime.plusMinutes(1);

        return oneMinuteAfterLastEntry.isBefore(logTime);
    }


    private void saveCourierLog(CourierTrackingRequest request, Long storeId) {
        try {
            CourierLog courierLog = new CourierLog();
            courierLog.setCourierId(request.getCourierId());
            courierLog.setLat(request.getLat());
            courierLog.setLng(request.getLng());
            courierLog.setTime(request.getTime());
            courierLog.setStoreId(storeId);

            courierLogRepository.save(courierLog);

            courierTrackingLogger.logCourierSave(request.getCourierId(), storeId);
        } catch (Exception e) {
            courierTrackingLogger.logCourierSaveError(request.getCourierId(), storeId, e.getMessage());
        }
    }

}
