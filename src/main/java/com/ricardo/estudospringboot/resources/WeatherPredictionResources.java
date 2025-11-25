package com.ricardo.estudospringboot.resources;

import com.ricardo.estudospringboot.dto.WeatherPrediction;
import com.ricardo.estudospringboot.dto.WeatherStatistics;
import com.ricardo.estudospringboot.entities.WeatherData;
import com.ricardo.estudospringboot.services.WeatherPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller for weather prediction operations.
 * Provides endpoints for predictions, statistics, and data management.
 */
@RestController
@RequestMapping(value = "/weather")
public class WeatherPredictionResources {

    @Autowired
    private WeatherPredictionService weatherPredictionService;

    /**
     * Generate weather prediction for a specific location.
     * 
     * @param location Location name
     * @param hoursAhead Hours into the future for prediction (default: 24)
     * @return Weather prediction with confidence score
     */
    @GetMapping(value = "/predict/{location}")
    public ResponseEntity<WeatherPrediction> getPrediction(
            @PathVariable String location,
            @RequestParam(defaultValue = "24") int hoursAhead) {
        WeatherPrediction prediction = weatherPredictionService.generatePrediction(location, hoursAhead);
        return ResponseEntity.ok(prediction);
    }

    /**
     * Generate batch predictions for multiple locations.
     * 
     * @param locations List of location names
     * @param hoursAhead Hours into the future for prediction
     * @return List of weather predictions
     */
    @PostMapping(value = "/predict/batch")
    public ResponseEntity<List<WeatherPrediction>> getBatchPredictions(
            @RequestBody List<String> locations,
            @RequestParam(defaultValue = "24") int hoursAhead) {
        List<WeatherPrediction> predictions = weatherPredictionService.batchPrediction(locations, hoursAhead);
        return ResponseEntity.ok(predictions);
    }

    /**
     * Get statistical analysis of weather data for a location.
     * 
     * @param location Location name
     * @return Statistical analysis including averages, min/max, and standard deviation
     */
    @GetMapping(value = "/statistics/{location}")
    public ResponseEntity<WeatherStatistics> getStatistics(@PathVariable String location) {
        WeatherStatistics statistics = weatherPredictionService.analyzeWeatherData(location);
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get all weather data for a location.
     * 
     * @param location Location name
     * @return List of weather data records
     */
    @GetMapping(value = "/data/{location}")
    public ResponseEntity<List<WeatherData>> getWeatherData(@PathVariable String location) {
        List<WeatherData> data = weatherPredictionService.getWeatherDataByLocation(location);
        return ResponseEntity.ok(data);
    }

    /**
     * Add new weather data record.
     * 
     * @param weatherData Weather data to save
     * @return Created weather data with generated ID
     */
    @PostMapping(value = "/data")
    public ResponseEntity<WeatherData> addWeatherData(@RequestBody WeatherData weatherData) {
        WeatherData saved = weatherPredictionService.saveWeatherData(weatherData);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    /**
     * Bulk import weather data records.
     * 
     * @param weatherDataList List of weather data records to save
     * @return List of saved weather data records
     */
    @PostMapping(value = "/data/bulk")
    public ResponseEntity<List<WeatherData>> bulkImportWeatherData(@RequestBody List<WeatherData> weatherDataList) {
        List<WeatherData> saved = weatherPredictionService.saveAllWeatherData(weatherDataList);
        return ResponseEntity.ok(saved);
    }
}
