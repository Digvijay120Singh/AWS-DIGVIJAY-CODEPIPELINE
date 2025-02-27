package com.caching.controller;

import com.caching.service.GeoCodingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * GeoCodingController is responsible for handling HTTP requests related to geocoding and reverse geocoding.
 * It provides endpoints to get geolocation data for a given address or coordinates.
 */

@RestController
@RequestMapping("/")
public class GeoCodingController {

    private static final Logger logger = LoggerFactory.getLogger(GeoCodingController.class);
    private final GeoCodingService geoCodingService;

    /**
     * Constructs a GeoCodingController with the provided GeoCodingService.
     *
     * @param geoCodingService Service used to perform geocoding operations.
     */
    @Autowired
    public GeoCodingController(GeoCodingService geoCodingService) {
        this.geoCodingService = geoCodingService;
    }

    /**
     * Endpoint to get geocode (latitude and longitude) for a given address.
     *
     * @param address The address to be geocoded.
     * @return A ResponseEntity containing the latitude and longitude of the address.
     */
    @GetMapping("/geocode")
    public ResponseEntity<Map<String, Double>> getGeocode(@RequestParam String address) {
        logger.info("Received geocoding request for address: {}", address);

        Map<String, Double> geocode = geoCodingService.getGeocode(address);

        logger.info("Longitude and Latitude result for address {}: {}", address, geocode);
        return ResponseEntity.ok(geocode);
    }

    @GetMapping("/digvijay")
    public ResponseEntity<String> getGeocode() {

        return ResponseEntity.ok("This is Digvijay  Singh Evaluated by Math");
    }


    //    @GetMapping("/geocoding/change")
//    public ResponseEntity<Map<String, Double>> getGeocodeChange(@RequestParam String address) {
//        logger.info("Received geocoding request for address: {}", address);
//        Map<String, Double> geocode = geoCodingService.getGeocode(address);
//
//        logger.info("Longitude and Latitude result for address {}: {}", address, geocode);
//
//        return ResponseEntity.ok(geocode);
//    }



    /**
     * Endpoint to get reverse geocode (address) for a given latitude and longitude.
     *
     * @param latitude  The latitude of the location to reverse geocode.
     * @param longitude The longitude of the location to reverse geocode.
     * @return A ResponseEntity containing the formatted response with the address and number.
     */
    @GetMapping("/reverse-geocoding")
    public ResponseEntity<String> getReverseGeocode(@RequestParam String latitude, @RequestParam String longitude) {
        logger.info("Received reverse geocoding request for coordinates: {} , {}", latitude, longitude);

        Map<String, String> response = geoCodingService.getReverseGeocode(latitude, longitude);

        String address = response.get("address");

        logger.info("Address for coordinates {} , {}: {}", latitude, longitude, address);
        return ResponseEntity.ok(address);
    }

}
