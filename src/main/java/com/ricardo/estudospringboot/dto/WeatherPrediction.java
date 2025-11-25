package com.ricardo.estudospringboot.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO representing a weather prediction result.
 */
public class WeatherPrediction implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime predictionTime;
    private String locationName;
    private Double predictedTemperature;
    private Double predictedHumidity;
    private Double predictedPressure;
    private Double predictedWindSpeed;
    private Double predictedPrecipitation;
    private Double confidenceScore;
    private String weatherCondition;
    private Long processingTimeMs;

    public WeatherPrediction() {
    }

    public WeatherPrediction(LocalDateTime predictionTime, String locationName,
                             Double predictedTemperature, Double predictedHumidity,
                             Double predictedPressure, Double predictedWindSpeed,
                             Double predictedPrecipitation, Double confidenceScore,
                             String weatherCondition, Long processingTimeMs) {
        this.predictionTime = predictionTime;
        this.locationName = locationName;
        this.predictedTemperature = predictedTemperature;
        this.predictedHumidity = predictedHumidity;
        this.predictedPressure = predictedPressure;
        this.predictedWindSpeed = predictedWindSpeed;
        this.predictedPrecipitation = predictedPrecipitation;
        this.confidenceScore = confidenceScore;
        this.weatherCondition = weatherCondition;
        this.processingTimeMs = processingTimeMs;
    }

    public LocalDateTime getPredictionTime() {
        return predictionTime;
    }

    public void setPredictionTime(LocalDateTime predictionTime) {
        this.predictionTime = predictionTime;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getPredictedTemperature() {
        return predictedTemperature;
    }

    public void setPredictedTemperature(Double predictedTemperature) {
        this.predictedTemperature = predictedTemperature;
    }

    public Double getPredictedHumidity() {
        return predictedHumidity;
    }

    public void setPredictedHumidity(Double predictedHumidity) {
        this.predictedHumidity = predictedHumidity;
    }

    public Double getPredictedPressure() {
        return predictedPressure;
    }

    public void setPredictedPressure(Double predictedPressure) {
        this.predictedPressure = predictedPressure;
    }

    public Double getPredictedWindSpeed() {
        return predictedWindSpeed;
    }

    public void setPredictedWindSpeed(Double predictedWindSpeed) {
        this.predictedWindSpeed = predictedWindSpeed;
    }

    public Double getPredictedPrecipitation() {
        return predictedPrecipitation;
    }

    public void setPredictedPrecipitation(Double predictedPrecipitation) {
        this.predictedPrecipitation = predictedPrecipitation;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public Long getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(Long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }
}
