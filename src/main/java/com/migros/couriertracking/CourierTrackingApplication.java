package com.migros.couriertracking;

import com.migros.couriertracking.controller.request.CourierTrackingRequest;
import com.migros.couriertracking.service.StoreService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;

@EntityScan("com.migros.couriertracking.entity")
@SpringBootApplication
public class CourierTrackingApplication implements CommandLineRunner {

	@Autowired
	private StoreService storeService;


	@Autowired @Lazy
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(CourierTrackingApplication.class, args);
	}

	@PostConstruct
	public void init() {
		try {
			storeService.insertStoresFromJson();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(String... args) {
		sendCourierLocationRequests();
	}

	private void sendCourierLocationRequests() {
		String baseUrl = "http://localhost:8080";

		// Request 1
		CourierTrackingRequest request1 = new CourierTrackingRequest();
		request1.setTime(LocalDateTime.parse("2024-02-21T10:00:02"));
		request1.setCourierId(123L);
		request1.setLat(40.9923307);
		request1.setLng(29.1244229);
		sendPostRequest(baseUrl + "/api/v1/tracking", request1);

		// Request 2
		CourierTrackingRequest request2 = new CourierTrackingRequest();
		request2.setTime(LocalDateTime.parse("2024-02-21T10:15:00"));
		request2.setCourierId(123L);
		request2.setLat(40.986106);
		request2.setLng(29.1161293);
		sendPostRequest(baseUrl + "/api/v1/tracking", request2);

		// Request 3
		CourierTrackingRequest request3 = new CourierTrackingRequest();
		request3.setTime(LocalDateTime.parse("2024-02-21T10:30:00"));
		request3.setCourierId(123L);
		request3.setLat(41.0066851);
		request3.setLng(28.6552262);
		sendPostRequest(baseUrl + "/api/v1/tracking", request3);

		// Request 4
		CourierTrackingRequest request4 = new CourierTrackingRequest();
		request4.setTime(LocalDateTime.parse("2024-02-21T10:45:00"));
		request4.setCourierId(123L);
		request4.setLat(41.055783);
		request4.setLng(29.0210292);
		sendPostRequest(baseUrl + "/api/v1/tracking", request4);

		// Request 5
		CourierTrackingRequest request5 = new CourierTrackingRequest();
		request5.setTime(LocalDateTime.parse("2024-02-21T11:00:00"));
		request5.setCourierId(123L);
		request5.setLat(40.9632463);
		request5.setLng(29.0630908);
		sendPostRequest(baseUrl + "/api/v1/tracking", request5);

		// Request 6 (101 meters away from Request 1)
		CourierTrackingRequest request6 = new CourierTrackingRequest();
		request6.setTime(LocalDateTime.parse("2024-02-21T10:05:00"));
		request6.setCourierId(123L);
		request6.setLat(40.9923307);
		request6.setLng(29.1244227);
		sendPostRequest(baseUrl + "/api/v1/tracking", request6);

		// Request 7 (over 1 minute from Request 1)
		CourierTrackingRequest request7 = new CourierTrackingRequest();
		request7.setTime(LocalDateTime.parse("2024-02-21T10:01:30"));
		request7.setCourierId(123L);
		request7.setLat(40.9923307);
		request7.setLng(29.1244229);
		sendPostRequest(baseUrl + "/api/v1/tracking", request7);

		// Request 8 (get total travel distance for courier 123)
		getTotalTravelDistance(baseUrl + "/api/v1/tracking/123");
	}

	private void sendPostRequest(String url, CourierTrackingRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CourierTrackingRequest> entity = new HttpEntity<>(request, headers);
		restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	private Double getTotalTravelDistance(String url) {
		ResponseEntity<Double> response = restTemplate.getForEntity(url, Double.class);
		return response.getBody();
	}

}
