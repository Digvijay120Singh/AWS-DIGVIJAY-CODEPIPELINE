package com.caching.exception.custom;

/**
 * Class to handle GeoCoding exceptions
 *
 * @return GeoCodingException
 */
public class GeoCodingException extends RuntimeException {
    public GeoCodingException(String message) {
        super(message);
    }


}

