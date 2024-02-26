package com.migros.couriertracking.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierTrackingRequest {
    @NotNull
    private LocalDateTime time;
    @NotNull
    private Long courierId;
    @NotNull
    private double lat;
    @NotNull
    private double lng;
}
