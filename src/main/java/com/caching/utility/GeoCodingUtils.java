package com.caching.utility;

import com.caching.exception.custom.GeoCodingResultNotFoundException;
import com.caching.exception.custom.InvalidGeoCodingRequestException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class for geocoding-related tasks, such as building API URLs and parsing API responses.
 */
public class GeoCodingUtils {

    private GeoCodingUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Builds the API URL with the given base URL, API key, and query parameters.
     *
     * @param baseUrl The base URL of the API.
     * @param apiKey  The API key for authentication.
     * @param params  The query parameters.
     * @return The constructed API URL.
     */
    public static String buildApiUrl(String baseUrl, String apiKey, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl).append("?access_key=").append(apiKey);
        params.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
        return urlBuilder.toString();
    }

    /**
     * Parses the response from the geocoding API to extract latitude and longitude.
     *
     * @param response The API response.
     * @return A map containing the latitude and longitude.
     * @throws InvalidGeoCodingRequestException If the response structure is invalid.
     * @throws GeoCodingResultNotFoundException If no results are found in the response.
     */
    public static Map<String, Double> parseGeoResponse(Map<String, Object> response) {
        if (response == null || !response.containsKey("data")) {
            throw new InvalidGeoCodingRequestException("Invalid response from geocoding API");
        }

        List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");
        if (data == null || data.isEmpty()) {
            throw new GeoCodingResultNotFoundException("No geocoding results found");
        }

        Map<String, Object> result = data.get(0);
        Double latitude = Optional.ofNullable(result.get("latitude"))
                .filter(Number.class::isInstance)
                .map(Number.class::cast)
                .map(Number::doubleValue)
                .orElseThrow(() -> new InvalidGeoCodingRequestException("Latitude is missing or invalid"));

        Double longitude = Optional.ofNullable(result.get("longitude"))
                .filter(Number.class::isInstance)
                .map(Number.class::cast)
                .map(Number::doubleValue)
                .orElseThrow(() -> new InvalidGeoCodingRequestException("Longitude is missing or invalid"));

        return Map.of("latitude", latitude, "longitude", longitude);
    }

    /**
     * Parses the response from the reverse geocoding API to extract the address.
     *
     * @param response The API response.
     * @return A map containing the address.
     * @throws InvalidGeoCodingRequestException If the response structure is invalid.
     * @throws GeoCodingResultNotFoundException If no results are found in the response.
     */
    public static Map<String, String> parseReverseGeoResponse(Map<String, Object> response) {
        if (response == null || !response.containsKey("data")) {
            throw new InvalidGeoCodingRequestException("Invalid response from reverse geocoding API");
        }

        List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");
        if (data == null || data.isEmpty()) {
            throw new GeoCodingResultNotFoundException("No reverse geocoding results found");
        }

        Map<String, Object> result = data.get(0);
        String address = (String) result.get("label");

        if (address == null || address.isEmpty()) {
            throw new InvalidGeoCodingRequestException("Address label is missing or invalid");
        }

        return Map.of("address", address);
    }
}
