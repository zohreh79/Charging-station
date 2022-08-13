package webService.charging.station.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webService.charging.station.model.CompaniesStationsResponse;
import webService.charging.station.model.CompanyResponse;
import webService.charging.station.model.entity.Company;
import webService.charging.station.model.entity.Station;
import webService.charging.station.repository.CompanyRepository;
import webService.charging.station.repository.StationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyRepository companyRepository;
    private final StationRepository stationRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, StationRepository stationRepository) {
        this.companyRepository = companyRepository;
        this.stationRepository = stationRepository;
    }

    public List<CompanyResponse> getInformation(List<Company> companies) {
        logger.info("CompanyService => getInformation");
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        companies.forEach(company -> {
            List<Station> stations = stationRepository.findByCompany(company);
            companyResponseList.add(new CompanyResponse(company.getId(), company.getName(), stations, company.getSubCompany()));
        });
        return companyResponseList;
    }

    public CompaniesStationsResponse getCompaniesStations(Company company) {
        logger.info("CompanyService => getCompaniesStations");

        CompaniesStationsResponse response = new CompaniesStationsResponse();
        List<CompaniesStationsResponse> subCompanyResponse = new ArrayList<>();

        List<Station> stationsList = stationRepository.findByCompany(company);
        if (!company.getSubCompany().isEmpty() && company.getSubCompany() != null) {
            company.getSubCompany().forEach(subCompany -> {
                subCompanyResponse.add(getCompaniesStations(subCompany));
            });
        }
        response.setCompanyName(company.getName());
        response.setStations(stationsList);
        response.setSubCompaniesStations(subCompanyResponse);
        return response;
    }

    public List<Company> getCompaniesList(List<String> companiesName, Company parentCompany) {
        logger.info("CompanyService => getCompaniesList");

        List<Company> companies = new ArrayList<>();
        if (!companiesName.isEmpty()) {

            companiesName.forEach(subCompaniesName -> {
                try {

                    Company companyFoundByName = companyRepository.findByName(subCompaniesName);
                    companies.add(Objects.requireNonNullElseGet(companyFoundByName, () -> new Company(subCompaniesName, parentCompany)));

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("CompanyService => getCompaniesList => error on finding company: " + e);
                }
            });
        }
        return companies;
    }

    public void checkSubCompanies(Company company, Company parentCompany) {
        List<String> subCompanyList = new ArrayList<>();
        subCompanyList.add(company.getName());

        if (parentCompany != null) {
            if (parentCompany.getSubCompany().isEmpty() || !parentCompany.getSubCompany().contains(company)) {
                parentCompany.setSubCompany(getCompaniesList(subCompanyList, parentCompany));
                companyRepository.save(parentCompany);
            }
        }
    }
}

