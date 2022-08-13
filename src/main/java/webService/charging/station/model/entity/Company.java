package webService.charging.station.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Company parentCompany;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sub_company",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "sub_id")})
    private List<Company> subCompany;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "company_station",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "station_id")})
    private List<Station> station;

    public Company() {
    }

    public Company(String name, Company parentCompany, List<Company> subCompany, List<Station> station) {
        this.name = name;
        this.parentCompany = parentCompany;
        this.subCompany = subCompany;
        this.station = station;
    }

    public Company(String name, Company parentCompany, List<Company> subCompany) {
        this.name = name;
        this.parentCompany = parentCompany;
        this.subCompany = subCompany;
    }

    public Company(String name, Company parentCompany) {
        this.name = name;
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

    public Company getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(Company parentCompany) {
        this.parentCompany = parentCompany;
    }

    public List<Company> getSubCompany() {
        return subCompany;
    }

    public void setSubCompany(List<Company> subCompany) {
        this.subCompany = subCompany;
    }

    public List<Station> getStation() {
        return station;
    }

    public void setStation(List<Station> station) {
        this.station = station;
    }
}
