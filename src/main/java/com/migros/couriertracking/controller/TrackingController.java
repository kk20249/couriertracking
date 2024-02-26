package com.migros.couriertracking.controller;

import com.migros.couriertracking.controller.request.CourierTrackingRequest;
import com.migros.couriertracking.service.CourierLogService;
import com.migros.couriertracking.util.CourierTrackingLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/tracking")
@RestController
public class TrackingController {

    @Autowired
    private CourierLogService courierLogService;

    private final CourierTrackingLogger courierTrackingLogger = CourierTrackingLogger.getInstance();


    @PostMapping
    public ResponseEntity<?> receiveCourierLocation(@Validated @RequestBody CourierTrackingRequest request) {
        courierTrackingLogger.receivedCourierLocation(request.getCourierId());
        courierLogService.trackCourierLocation(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{courierId}")
    public ResponseEntity<Double> getTotalTravelDistance(@PathVariable Long courierId) {
        courierTrackingLogger.fetchingCourierTravelDistance(courierId);
        Double totalTravelDistance = courierLogService.getTotalTravelDistance(courierId);
        courierTrackingLogger.fetchedCourierTravelDistance(courierId, totalTravelDistance);
        return new ResponseEntity<>(totalTravelDistance, HttpStatus.OK);
    }
}
