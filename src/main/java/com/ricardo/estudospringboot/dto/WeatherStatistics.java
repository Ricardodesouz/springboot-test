package com.ricardo.estudospringboot.dto;

import java.io.Serializable;

/**
 * DTO representing statistical analysis of weather data.
 */
public class WeatherStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    private String locationName;
    private Long dataPointsAnalyzed;
    private Double avgTemperature;
    private Double minTemperature;
    private Double maxTemperature;
    private Double stdDevTemperature;
    private Double avgHumidity;
    private Double avgPressure;
    private Double avgWindSpeed;
    private Double totalPrecipitation;
    private Long processingTimeMs;

    public WeatherStatistics() {
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getDataPointsAnalyzed() {
        return dataPointsAnalyzed;
    }

    public void setDataPointsAnalyzed(Long dataPointsAnalyzed) {
        this.dataPointsAnalyzed = dataPointsAnalyzed;
    }

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(Double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getStdDevTemperature() {
        return stdDevTemperature;
    }

    public void setStdDevTemperature(Double stdDevTemperature) {
        this.stdDevTemperature = stdDevTemperature;
    }

    public Double getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(Double avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    public Double getAvgPressure() {
        return avgPressure;
    }

    public void setAvgPressure(Double avgPressure) {
        this.avgPressure = avgPressure;
    }

    public Double getAvgWindSpeed() {
        return avgWindSpeed;
    }

    public void setAvgWindSpeed(Double avgWindSpeed) {
        this.avgWindSpeed = avgWindSpeed;
    }

    public Double getTotalPrecipitation() {
        return totalPrecipitation;
    }

    public void setTotalPrecipitation(Double totalPrecipitation) {
        this.totalPrecipitation = totalPrecipitation;
    }

    public Long getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(Long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }
}
