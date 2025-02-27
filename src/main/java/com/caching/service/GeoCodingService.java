package com.caching.service;

import com.caching.repository.GeoCodingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service responsible for managing geocoding and reverse geocoding operations.
 * It uses caching to optimize performance and reduce API calls.
 */
@Service
public class GeoCodingService {

    private static final Logger logger = LoggerFactory.getLogger(GeoCodingService.class);
    private final GeoCodingRepository geoCodingRepository;

    /**
     * Constructor for dependency injection of the GeoCodingUtils and CacheManager.
     *
     * @param geoCodingRepository The repository handling API interactions.
     */
    public GeoCodingService(GeoCodingRepository geoCodingRepository) {
        this.geoCodingRepository = geoCodingRepository;
    }

    /**
     * Retrieves geocoding data (latitude and longitude) for the given address.
     * Results are cached to avoid redundant API calls for the same address.
     *
     * @param address The address to geocode.
     * @return A map containing latitude and longitude values.
     */
    @Cacheable(value = "geocoding", key = "#address", unless = "#address.equalsIgnoreCase('goa')")
    public Map<String, Double> getGeocode(String address) {
        logger.info("Fetching geocoding data for address: {}", address);
        return geoCodingRepository.fetchGeocode(address);
    }

    /**
     * Retrieves reverse geocoding data (address) for the given latitude and longitude.
     * Results are cached to avoid redundant API calls for the same coordinates.
     *
     * @param latitude  The latitude of the location.
     * @param longitude The longitude of the location.
     * @return A map containing the address.
     */
    @Cacheable(value = "reverse-geocoding", key = "{#latitude, #longitude}")
    public Map<String, String> getReverseGeocode(String latitude, String longitude) {
        logger.info("Fetching reverse geocoding data for coordinates: {}, {}", latitude, longitude);
        return geoCodingRepository.fetchReverseGeocode(latitude, longitude);
    }


    /**
     * Evicts all entries in the geocoding cache at a fixed interval of 6 hours.
     * This ensures that cached data remains fresh.
     */
    @CacheEvict(value = "geocoding", allEntries = true)
    @Scheduled(fixedRate = 21600000) // 6 hours
    public void evictAllGeoCaches() {
        logger.info("Evicting all geocoding cache entries.");
    }

    /**
     * Evicts all entries in the reverse geocoding cache at a fixed interval of 6 hours.
     * This ensures that cached data remains fresh.
     */
    @CacheEvict(value = "reverse-geocoding", allEntries = true)
    @Scheduled(fixedRate = 21600000) // 6 hours
    public void evictAllReverseGeoCaches() {
        logger.info("Evicting all reverse geocoding cache entries.");
    }
}
