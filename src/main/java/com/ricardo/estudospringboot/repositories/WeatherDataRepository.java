package com.ricardo.estudospringboot.repositories;

import com.ricardo.estudospringboot.entities.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for WeatherData with optimized queries for efficient data retrieval.
 */
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    /**
     * Find weather data within a time range for a specific location.
     */
    List<WeatherData> findByLocationNameAndTimestampBetweenOrderByTimestampAsc(
            String locationName, LocalDateTime start, LocalDateTime end);

    /**
     * Find weather data within geographic bounds and time range.
     */
    @Query("SELECT w FROM WeatherData w WHERE w.latitude BETWEEN :minLat AND :maxLat " +
           "AND w.longitude BETWEEN :minLon AND :maxLon " +
           "AND w.timestamp BETWEEN :start AND :end ORDER BY w.timestamp ASC")
    List<WeatherData> findByGeoBoundsAndTimeRange(
            @Param("minLat") Double minLat,
            @Param("maxLat") Double maxLat,
            @Param("minLon") Double minLon,
            @Param("maxLon") Double maxLon,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    /**
     * Find recent weather data for a location.
     */
    List<WeatherData> findTop100ByLocationNameOrderByTimestampDesc(String locationName);

    /**
     * Find all data for a location.
     */
    List<WeatherData> findByLocationNameOrderByTimestampAsc(String locationName);
}
