package com.migros.couriertracking.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CourierTrackingLogger {
    private static final Logger logger = LogManager.getLogger(CourierTrackingLogger.class);
    private static CourierTrackingLogger instance = null;

    private CourierTrackingLogger() {
    }

    public static synchronized CourierTrackingLogger getInstance() {
        if (instance == null) {
            instance = new CourierTrackingLogger();
        }
        return instance;
    }

    public void logCourierEntry(Long courierId, Long storeId) {
        logger.info("Courier {} entered the radius of store {}.", courierId, storeId);
    }

    public void logCourierEnteredOver1Minute(Long courierId, Long storeId) {
        logger.info("Courier {} entered the radius of store {} over 1 minute.", courierId, storeId);
    }

    public void logCourierNotEntered(Long courierId, Long storeId) {
        logger.info("Courier {} did not enter the radius of the store {}.", courierId, storeId);
    }

    public void logCourierSave(Long courierId, Long storeId) {
        logger.info("Courier log saved for courier {} at store {}.", courierId, storeId);
    }

    public void logCourierSaveError(Long courierId, Long storeId, String errorMessage) {
        logger.error("Error occurred while saving courier log for courier {} at store {}: {}.", courierId, storeId, errorMessage);
    }

    public void getAllStores() {
        logger.info("Getting all stores");
    }

    public void receivedCourierLocation(Long courierId) {
        logger.info("Received courier location for courier ID: {}", courierId);
    }

    public void fetchingCourierTravelDistance(Long courierId) {
        logger.info("Fetching total travel distance for courier ID: {}", courierId);
    }

    public void fetchedCourierTravelDistance(Long courierId, Double totalTravelDistance) {
        logger.info("Fetched total travel distance for courier ID {}: {} meters", courierId, totalTravelDistance);
    }

}