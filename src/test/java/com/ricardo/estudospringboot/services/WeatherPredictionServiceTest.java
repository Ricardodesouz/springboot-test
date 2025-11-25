package com.ricardo.estudospringboot.services;

import com.ricardo.estudospringboot.dto.WeatherPrediction;
import com.ricardo.estudospringboot.dto.WeatherStatistics;
import com.ricardo.estudospringboot.entities.WeatherData;
import com.ricardo.estudospringboot.repositories.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherPredictionServiceTest {

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @InjectMocks
    private WeatherPredictionService weatherPredictionService;

    private List<WeatherData> sampleWeatherData;

    @BeforeEach
    void setUp() {
        sampleWeatherData = createSampleWeatherData();
    }

    @Test
    void testGeneratePredictionWithData() {
        when(weatherDataRepository.findTop100ByLocationNameOrderByTimestampDesc("São Paulo"))
                .thenReturn(sampleWeatherData);

        WeatherPrediction prediction = weatherPredictionService.generatePrediction("São Paulo", 24);

        assertNotNull(prediction);
        assertEquals("São Paulo", prediction.getLocationName());
        assertNotNull(prediction.getPredictedTemperature());
        assertNotNull(prediction.getConfidenceScore());
        assertTrue(prediction.getProcessingTimeMs() >= 0);
    }

    @Test
    void testGeneratePredictionWithNoData() {
        when(weatherDataRepository.findTop100ByLocationNameOrderByTimestampDesc("Unknown"))
                .thenReturn(Collections.emptyList());

        WeatherPrediction prediction = weatherPredictionService.generatePrediction("Unknown", 24);

        assertNotNull(prediction);
        assertEquals("Unknown", prediction.getLocationName());
        assertEquals(0.0, prediction.getConfidenceScore());
        assertEquals("Unknown - No historical data", prediction.getWeatherCondition());
    }

    @Test
    void testAnalyzeWeatherDataWithData() {
        when(weatherDataRepository.findByLocationNameOrderByTimestampAsc("São Paulo"))
                .thenReturn(sampleWeatherData);

        WeatherStatistics statistics = weatherPredictionService.analyzeWeatherData("São Paulo");

        assertNotNull(statistics);
        assertEquals("São Paulo", statistics.getLocationName());
        assertEquals(Long.valueOf(sampleWeatherData.size()), statistics.getDataPointsAnalyzed());
        assertNotNull(statistics.getAvgTemperature());
        assertNotNull(statistics.getMinTemperature());
        assertNotNull(statistics.getMaxTemperature());
        assertTrue(statistics.getProcessingTimeMs() >= 0);
    }

    @Test
    void testAnalyzeWeatherDataWithNoData() {
        when(weatherDataRepository.findByLocationNameOrderByTimestampAsc("Unknown"))
                .thenReturn(Collections.emptyList());

        WeatherStatistics statistics = weatherPredictionService.analyzeWeatherData("Unknown");

        assertNotNull(statistics);
        assertEquals("Unknown", statistics.getLocationName());
        assertEquals(Long.valueOf(0), statistics.getDataPointsAnalyzed());
    }

    @Test
    void testBatchPrediction() {
        when(weatherDataRepository.findTop100ByLocationNameOrderByTimestampDesc(anyString()))
                .thenReturn(sampleWeatherData);

        List<String> locations = Arrays.asList("São Paulo", "Rio de Janeiro", "Brasília");
        List<WeatherPrediction> predictions = weatherPredictionService.batchPrediction(locations, 24);

        assertNotNull(predictions);
        assertEquals(3, predictions.size());
    }

    @Test
    void testProcessingEfficiency() {
        // Create large dataset to test parallel processing efficiency
        List<WeatherData> largeDataset = createLargeDataset(1000);
        when(weatherDataRepository.findTop100ByLocationNameOrderByTimestampDesc("Test"))
                .thenReturn(largeDataset);

        long startTime = System.currentTimeMillis();
        WeatherPrediction prediction = weatherPredictionService.generatePrediction("Test", 24);
        long endTime = System.currentTimeMillis();

        assertNotNull(prediction);
        // Processing should be reasonably fast even with large dataset
        assertTrue(endTime - startTime < 5000, "Processing took too long");
    }

    private List<WeatherData> createSampleWeatherData() {
        List<WeatherData> data = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 50; i++) {
            WeatherData wd = new WeatherData();
            wd.setId((long) i);
            wd.setTimestamp(now.minusHours(i));
            wd.setTemperature(25.0 + Math.random() * 10 - 5);
            wd.setHumidity(60.0 + Math.random() * 20 - 10);
            wd.setPressure(1013.0 + Math.random() * 10 - 5);
            wd.setWindSpeed(10.0 + Math.random() * 5);
            wd.setPrecipitation(Math.random() * 5);
            wd.setCloudCover(50.0 + Math.random() * 30 - 15);
            wd.setLatitude(-23.55);
            wd.setLongitude(-46.63);
            wd.setLocationName("São Paulo");
            data.add(wd);
        }

        return data;
    }

    private List<WeatherData> createLargeDataset(int size) {
        List<WeatherData> data = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < size; i++) {
            WeatherData wd = new WeatherData();
            wd.setId((long) i);
            wd.setTimestamp(now.minusHours(i));
            wd.setTemperature(20.0 + Math.random() * 15);
            wd.setHumidity(50.0 + Math.random() * 40);
            wd.setPressure(1010.0 + Math.random() * 20);
            wd.setWindSpeed(5.0 + Math.random() * 20);
            wd.setPrecipitation(Math.random() * 10);
            wd.setCloudCover(Math.random() * 100);
            wd.setLatitude(-23.55);
            wd.setLongitude(-46.63);
            wd.setLocationName("Test");
            data.add(wd);
        }

        return data;
    }
}
