package com.caching.utility.constant;

/**
 * Enum for holding PositionStack API constants and URL.
 */
public enum GeoCodingEnum {
    API_KEY("87cf2b6cbd40c90fa1a2be432887a276"),
    GEOCODING_URL("https://api.positionstack.com/v1/forward"),
    REVERSE_GEOCODING_URL("https://api.positionstack.com/v1/reverse");

    private final String value;

    // Constructor
    GeoCodingEnum(String value) {
        this.value = value;
    }

    // Getter to retrieve the value of each constant
    public String getValue() {
        return value;
    }
}
