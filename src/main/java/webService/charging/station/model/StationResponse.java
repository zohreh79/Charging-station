package webService.charging.station.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import webService.charging.station.model.entity.Company;

public class StationResponse {

    private int id;
    private String name;
    private double latitude;
    private double longitude;
    @JsonIgnore
    private Company company;

    public StationResponse() {
    }

    public StationResponse(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StationResponse(int id, String name, double latitude, double longitude, Company company) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
