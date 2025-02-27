package com.caching.exception.custom;

/**
 * Exception class for handling cases where no geocoding or reverse geocoding results are found.
 * This exception is thrown when the external API does not return any valid results for the requested address.
 *
 * @return A message indicating that no geocoding or reverse geocoding results were found.
 */
public class GeoCodingResultNotFoundException extends GeoCodingException {
    public GeoCodingResultNotFoundException(String message) {
        super(message);
    }
}
