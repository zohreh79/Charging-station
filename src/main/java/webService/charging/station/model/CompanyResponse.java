package webService.charging.station.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import webService.charging.station.model.entity.Company;
import webService.charging.station.model.entity.Station;

import java.util.List;

public class CompanyResponse {

    private int id;
    private String name;
    private List<Station> stations;
    private List<Company> subCompaniesInfo;
    @JsonIgnore
    private Company parentCompany;

    public CompanyResponse() {
    }

    public CompanyResponse(int id, String name, List<Station> stations, List<Company> subCompaniesInfo) {
        this.id = id;
        this.name = name;
        this.stations = stations;
        this.subCompaniesInfo = subCompaniesInfo;
    }

    public CompanyResponse(int id, String name, List<Station> stations, List<Company> subCompaniesInfo, Company parentCompany) {
        this.id = id;
        this.name = name;
        this.stations = stations;
        this.subCompaniesInfo = subCompaniesInfo;
        this.parentCompany = parentCompany;
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

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public List<Company> getSubCompaniesInfo() {
        return subCompaniesInfo;
    }

    public void setSubCompaniesInfo(List<Company> subCompaniesInfo) {
        this.subCompaniesInfo = subCompaniesInfo;
    }

    public Company getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(Company parentCompany) {
        this.parentCompany = parentCompany;
    }
}
