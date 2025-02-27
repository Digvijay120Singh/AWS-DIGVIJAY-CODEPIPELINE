package com.caching.exception.custom;

/**
 * Exception class that represents an invalid geocoding or reverse geocoding request.
 * This exception is thrown when an invalid or malformed request is made to the external geocoding API,
 * or when required data in the API response is missing or invalid.
 *
 * @return A message indicating that the geocoding or reverse geocoding request is invalid.
 */
public class InvalidGeoCodingRequestException extends GeoCodingException {
    public InvalidGeoCodingRequestException(String message) {
        super(message);
    }
}
