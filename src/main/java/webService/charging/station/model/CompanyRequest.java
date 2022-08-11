package webService.charging.station.model;

import java.util.List;

public class CompanyRequest {

    private String name;
    private String parentCompany;
    private List<String> subCompany;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public List<String> getSubCompany() {
        return subCompany;
    }

    public void setSubCompany(List<String> subCompany) {
        this.subCompany = subCompany;
    }
}
