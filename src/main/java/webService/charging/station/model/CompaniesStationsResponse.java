package webService.charging.station.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import webService.charging.station.model.entity.Station;

import java.util.List;

public class CompaniesStationsResponse {

    private String companyName;
    private List<Station> stations;
    private List<CompaniesStationsResponse> subCompaniesStations;

    public CompaniesStationsResponse() {
    }

    public CompaniesStationsResponse(String companyName, List<Station> stations) {
        this.companyName = companyName;
        this.stations = stations;
    }

    public CompaniesStationsResponse(String companyName, List<Station> stations, List<CompaniesStationsResponse> subCompaniesStations) {
        this.companyName = companyName;
        this.stations = stations;
        this.subCompaniesStations = subCompaniesStations;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public List<CompaniesStationsResponse> getSubCompaniesStations() {
        return subCompaniesStations;
    }

    public void setSubCompaniesStations(List<CompaniesStationsResponse> subCompaniesStations) {
        this.subCompaniesStations = subCompaniesStations;
    }
}
