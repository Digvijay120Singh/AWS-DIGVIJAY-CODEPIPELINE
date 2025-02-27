package com.caching.repository;

import com.caching.utility.constant.GeoCodingEnum;
import com.caching.exception.custom.GeoCodingResultNotFoundException;
import com.caching.exception.custom.InvalidGeoCodingRequestException;
import com.caching.utility.GeoCodingUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Repository for handling communication with the PositionStack API.
 * It provides methods for geocoding and reverse geocoding operations.
 */
@Repository
public class GeoCodingRepository {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Fetches geocoding data (latitude and longitude) for a given address from the PositionStack API.
     *
     * @param address The address to be geocoded.
     * @return A map containing the latitude and longitude.
     * @throws InvalidGeoCodingRequestException If the request to the API is invalid.
     * @throws GeoCodingResultNotFoundException If no geocoding results are found.
     */
    public Map<String, Double> fetchGeocode(String address) {
        String url = GeoCodingUtils.buildApiUrl(
                GeoCodingEnum.GEOCODING_URL.getValue(),
                GeoCodingEnum.API_KEY.getValue(),
                Map.of("query", address)
        );

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return GeoCodingUtils.parseGeoResponse(response);
        } catch (HttpClientErrorException ex) {
            throw new InvalidGeoCodingRequestException("Invalid geocoding request: " + ex.getMessage());
        }
    }

    /**
     * Fetches reverse geocoding data (address) for given latitude and longitude from the PositionStack API.
     *
     * @param latitude  The latitude of the location.
     * @param longitude The longitude of the location.
     * @return A map containing the address.
     * @throws InvalidGeoCodingRequestException If the request to the API is invalid.
     * @throws GeoCodingResultNotFoundException If no reverse geocoding results are found.
     */
    public Map<String, String> fetchReverseGeocode(String latitude, String longitude) {
        String url = GeoCodingUtils.buildApiUrl(
                GeoCodingEnum.REVERSE_GEOCODING_URL.getValue(),
                GeoCodingEnum.API_KEY.getValue(),
                Map.of("query", latitude + "," + longitude)
        );

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return GeoCodingUtils.parseReverseGeoResponse(response);
        } catch (HttpClientErrorException ex) {
            throw new InvalidGeoCodingRequestException("Invalid reverse geocoding request: " + ex.getMessage());
        }
    }
}
