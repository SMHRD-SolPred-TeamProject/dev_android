package com.example.solarpred;

public class DashBoard {

    private int r_seq;
    private String r_date;
    private double r_temperature;
    private double r_precipitaion;
    private double r_wind;
    private double r_humidity;
    private double r_radiation;
    private double r_total_cloudiness;
    private double r_visibility;
    private double r_ground_temp;
    private int r_aod;
    private String r_id;

    public DashBoard(){}

    public DashBoard(int r_seq, String r_date, double r_temperature, double r_precipitaion, double r_wind, double r_humidity, double r_radiation, double r_total_cloudiness, double r_visibility, double r_ground_temp, int r_aod, String r_id) {
        this.r_seq = r_seq;
        this.r_date = r_date;
        this.r_temperature = r_temperature;
        this.r_precipitaion = r_precipitaion;
        this.r_wind = r_wind;
        this.r_humidity = r_humidity;
        this.r_radiation = r_radiation;
        this.r_total_cloudiness = r_total_cloudiness;
        this.r_visibility = r_visibility;
        this.r_ground_temp = r_ground_temp;
        this.r_aod = r_aod;
        this.r_id = r_id;
    }

    public int getR_seq() {
        return r_seq;
    }

    public void setR_seq(int r_seq) {
        this.r_seq = r_seq;
    }

    public String getR_date() {
        return r_date;
    }

    public void setR_date(String r_date) {
        this.r_date = r_date;
    }

    public double getR_temperature() {
        return r_temperature;
    }

    public void setR_temperature(double r_temperature) {
        this.r_temperature = r_temperature;
    }

    public double getR_precipitaion() {
        return r_precipitaion;
    }

    public void setR_precipitaion(double r_precipitaion) {
        this.r_precipitaion = r_precipitaion;
    }

    public double getR_wind() {
        return r_wind;
    }

    public void setR_wind(double r_wind) {
        this.r_wind = r_wind;
    }

    public double getR_humidity() {
        return r_humidity;
    }

    public void setR_humidity(double r_humidity) {
        this.r_humidity = r_humidity;
    }

    public double getR_radiation() {
        return r_radiation;
    }

    public void setR_radiation(double r_radiation) {
        this.r_radiation = r_radiation;
    }

    public double getR_total_cloudiness() {
        return r_total_cloudiness;
    }

    public void setR_total_cloudiness(double r_total_cloudiness) {
        this.r_total_cloudiness = r_total_cloudiness;
    }

    public double getR_visibility() {
        return r_visibility;
    }

    public void setR_visibility(double r_visibility) {
        this.r_visibility = r_visibility;
    }

    public double getR_ground_temp() {
        return r_ground_temp;
    }

    public void setR_ground_temp(double r_ground_temp) {
        this.r_ground_temp = r_ground_temp;
    }

    public int getR_aod() {
        return r_aod;
    }

    public void setR_aod(int r_aod) {
        this.r_aod = r_aod;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }
}
