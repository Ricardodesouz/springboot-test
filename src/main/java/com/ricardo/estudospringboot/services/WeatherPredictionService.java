package com.ricardo.estudospringboot.services;

import com.ricardo.estudospringboot.dto.WeatherPrediction;
import com.ricardo.estudospringboot.dto.WeatherStatistics;
import com.ricardo.estudospringboot.entities.WeatherData;
import com.ricardo.estudospringboot.repositories.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service for weather prediction with efficient processing of large meteorological datasets.
 * Uses parallel streams and optimized algorithms for fast data processing.
 */
@Service
public class WeatherPredictionService {

    private final WeatherDataRepository weatherDataRepository;
    private final ExecutorService executorService;

    @Autowired
    public WeatherPredictionService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
        // Use available processors for optimal parallel processing
        this.executorService = Executors.newWorkStealingPool();
    }

    /**
     * Generate weather prediction for a location using historical data analysis.
     * Uses exponential moving average for trend prediction.
     */
    public WeatherPrediction generatePrediction(String locationName, int hoursAhead) {
        long startTime = System.currentTimeMillis();

        List<WeatherData> historicalData = weatherDataRepository
                .findTop100ByLocationNameOrderByTimestampDesc(locationName);

        if (historicalData.isEmpty()) {
            return createEmptyPrediction(locationName, hoursAhead, startTime);
        }

        // Use parallel stream for efficient calculation of weighted averages
        double alpha = 0.3; // Smoothing factor for exponential moving average

        // Calculate predictions using parallel processing
        CompletableFuture<Double> tempFuture = CompletableFuture.supplyAsync(
                () -> calculateExponentialMovingAverage(historicalData, WeatherData::getTemperature, alpha),
                executorService);

        CompletableFuture<Double> humidityFuture = CompletableFuture.supplyAsync(
                () -> calculateExponentialMovingAverage(historicalData, WeatherData::getHumidity, alpha),
                executorService);

        CompletableFuture<Double> pressureFuture = CompletableFuture.supplyAsync(
                () -> calculateExponentialMovingAverage(historicalData, WeatherData::getPressure, alpha),
                executorService);

        CompletableFuture<Double> windFuture = CompletableFuture.supplyAsync(
                () -> calculateExponentialMovingAverage(historicalData, WeatherData::getWindSpeed, alpha),
                executorService);

        CompletableFuture<Double> precipFuture = CompletableFuture.supplyAsync(
                () -> calculateExponentialMovingAverage(historicalData, WeatherData::getPrecipitation, alpha),
                executorService);

        // Wait for all calculations to complete
        CompletableFuture.allOf(tempFuture, humidityFuture, pressureFuture, windFuture, precipFuture).join();

        double predictedTemp = tempFuture.join();
        double predictedHumidity = humidityFuture.join();
        double predictedPressure = pressureFuture.join();
        double predictedWind = windFuture.join();
        double predictedPrecip = precipFuture.join();

        // Calculate confidence based on data variance and sample size
        double confidence = calculateConfidence(historicalData);

        // Determine weather condition based on predictions
        String condition = determineWeatherCondition(predictedTemp, predictedHumidity,
                predictedPressure, predictedPrecip);

        long processingTime = System.currentTimeMillis() - startTime;

        return new WeatherPrediction(
                LocalDateTime.now().plusHours(hoursAhead),
                locationName,
                round(predictedTemp, 2),
                round(predictedHumidity, 2),
                round(predictedPressure, 2),
                round(predictedWind, 2),
                round(predictedPrecip, 2),
                round(confidence, 2),
                condition,
                processingTime
        );
    }

    /**
     * Perform statistical analysis on weather data for a location.
     * Uses parallel streams for efficient processing of large datasets.
     */
    public WeatherStatistics analyzeWeatherData(String locationName) {
        long startTime = System.currentTimeMillis();

        List<WeatherData> data = weatherDataRepository.findByLocationNameOrderByTimestampAsc(locationName);

        if (data.isEmpty()) {
            WeatherStatistics emptyStats = new WeatherStatistics();
            emptyStats.setLocationName(locationName);
            emptyStats.setDataPointsAnalyzed(0L);
            emptyStats.setProcessingTimeMs(System.currentTimeMillis() - startTime);
            return emptyStats;
        }

        // Use parallel stream for efficient statistical calculations
        DoubleSummaryStatistics tempStats = data.parallelStream()
                .filter(w -> w.getTemperature() != null)
                .mapToDouble(WeatherData::getTemperature)
                .summaryStatistics();

        // Calculate standard deviation in parallel
        double avgTemp = tempStats.getAverage();
        double variance = data.parallelStream()
                .filter(w -> w.getTemperature() != null)
                .mapToDouble(w -> Math.pow(w.getTemperature() - avgTemp, 2))
                .average()
                .orElse(0.0);
        double stdDev = Math.sqrt(variance);

        // Calculate other statistics in parallel
        CompletableFuture<Double> avgHumidity = CompletableFuture.supplyAsync(
                () -> data.parallelStream()
                        .filter(w -> w.getHumidity() != null)
                        .mapToDouble(WeatherData::getHumidity)
                        .average()
                        .orElse(0.0),
                executorService);

        CompletableFuture<Double> avgPressure = CompletableFuture.supplyAsync(
                () -> data.parallelStream()
                        .filter(w -> w.getPressure() != null)
                        .mapToDouble(WeatherData::getPressure)
                        .average()
                        .orElse(0.0),
                executorService);

        CompletableFuture<Double> avgWindSpeed = CompletableFuture.supplyAsync(
                () -> data.parallelStream()
                        .filter(w -> w.getWindSpeed() != null)
                        .mapToDouble(WeatherData::getWindSpeed)
                        .average()
                        .orElse(0.0),
                executorService);

        CompletableFuture<Double> totalPrecip = CompletableFuture.supplyAsync(
                () -> data.parallelStream()
                        .filter(w -> w.getPrecipitation() != null)
                        .mapToDouble(WeatherData::getPrecipitation)
                        .sum(),
                executorService);

        CompletableFuture.allOf(avgHumidity, avgPressure, avgWindSpeed, totalPrecip).join();

        WeatherStatistics stats = new WeatherStatistics();
        stats.setLocationName(locationName);
        stats.setDataPointsAnalyzed((long) data.size());
        stats.setAvgTemperature(round(tempStats.getAverage(), 2));
        stats.setMinTemperature(round(tempStats.getMin(), 2));
        stats.setMaxTemperature(round(tempStats.getMax(), 2));
        stats.setStdDevTemperature(round(stdDev, 2));
        stats.setAvgHumidity(round(avgHumidity.join(), 2));
        stats.setAvgPressure(round(avgPressure.join(), 2));
        stats.setAvgWindSpeed(round(avgWindSpeed.join(), 2));
        stats.setTotalPrecipitation(round(totalPrecip.join(), 2));
        stats.setProcessingTimeMs(System.currentTimeMillis() - startTime);

        return stats;
    }

    /**
     * Batch process weather data for multiple locations efficiently.
     */
    public List<WeatherPrediction> batchPrediction(List<String> locationNames, int hoursAhead) {
        return locationNames.parallelStream()
                .map(location -> generatePrediction(location, hoursAhead))
                .toList();
    }

    /**
     * Save weather data.
     */
    public WeatherData saveWeatherData(WeatherData weatherData) {
        return weatherDataRepository.save(weatherData);
    }

    /**
     * Save multiple weather data records efficiently.
     */
    public List<WeatherData> saveAllWeatherData(List<WeatherData> weatherDataList) {
        return weatherDataRepository.saveAll(weatherDataList);
    }

    /**
     * Get all weather data for a location.
     */
    public List<WeatherData> getWeatherDataByLocation(String locationName) {
        return weatherDataRepository.findByLocationNameOrderByTimestampAsc(locationName);
    }

    /**
     * Find weather data by ID.
     */
    public Optional<WeatherData> findById(Long id) {
        return weatherDataRepository.findById(id);
    }

    // Private helper methods

    private <T> double calculateExponentialMovingAverage(List<T> data,
                                                         java.util.function.Function<T, Double> extractor,
                                                         double alpha) {
        double ema = 0.0;
        boolean first = true;

        for (T item : data) {
            Double value = extractor.apply(item);
            if (value != null) {
                if (first) {
                    ema = value;
                    first = false;
                } else {
                    ema = alpha * value + (1 - alpha) * ema;
                }
            }
        }
        return ema;
    }

    private double calculateConfidence(List<WeatherData> data) {
        if (data.size() < 10) {
            return 0.5; // Low confidence with few data points
        }

        // Calculate coefficient of variation for temperature
        DoubleSummaryStatistics stats = data.parallelStream()
                .filter(w -> w.getTemperature() != null)
                .mapToDouble(WeatherData::getTemperature)
                .summaryStatistics();

        if (stats.getCount() == 0 || stats.getAverage() == 0) {
            return 0.5;
        }

        double avg = stats.getAverage();
        double variance = data.parallelStream()
                .filter(w -> w.getTemperature() != null)
                .mapToDouble(w -> Math.pow(w.getTemperature() - avg, 2))
                .average()
                .orElse(0.0);
        double cv = Math.sqrt(variance) / Math.abs(avg);

        // Lower variance = higher confidence
        double confidence = Math.max(0.5, Math.min(0.99, 1.0 - cv));

        // Boost confidence with more data points
        double sampleBonus = Math.min(0.1, data.size() / 1000.0);

        return Math.min(0.99, confidence + sampleBonus);
    }

    private String determineWeatherCondition(double temp, double humidity,
                                             double pressure, double precipitation) {
        if (precipitation > 5.0) {
            return "Rainy";
        } else if (precipitation > 0.5) {
            return "Light Rain";
        } else if (humidity > 85 && pressure < 1010) {
            return "Cloudy";
        } else if (humidity < 40 && temp > 30) {
            return "Hot and Dry";
        } else if (temp < 0) {
            return "Freezing";
        } else if (temp < 10) {
            return "Cold";
        } else if (temp > 25 && humidity < 60) {
            return "Sunny";
        } else {
            return "Partly Cloudy";
        }
    }

    private WeatherPrediction createEmptyPrediction(String locationName, int hoursAhead, long startTime) {
        return new WeatherPrediction(
                LocalDateTime.now().plusHours(hoursAhead),
                locationName,
                null, null, null, null, null,
                0.0,
                "Unknown - No historical data",
                System.currentTimeMillis() - startTime
        );
    }

    private double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
