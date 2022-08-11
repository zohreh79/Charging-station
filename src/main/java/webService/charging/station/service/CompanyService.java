package webService.charging.station.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webService.charging.station.model.CompaniesStationsResponse;
import webService.charging.station.model.CompanyResponse;
import webService.charging.station.model.entity.Company;
import webService.charging.station.repository.CompanyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyResponse> getInformation(List<Company> companies) {
        logger.info("CompanyService => getInformation");
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        companies.forEach(company -> companyResponseList.add(new CompanyResponse(company.getId(), company.getName(), company.getStation(), company.getSubCompany())));
        return companyResponseList;

    }

    public CompaniesStationsResponse getCompaniesStations(Company company) {
        logger.info("CompanyService => getCompaniesStations");

        CompaniesStationsResponse response = new CompaniesStationsResponse();
        if (company.getSubCompany() != null) {
            company.getSubCompany().forEach(subCompany -> response.getSubCompaniesStations().add(getCompaniesStations(subCompany)));
        } else {
            return new CompaniesStationsResponse(company.getName(), company.getStation());
        }
        response.setCompanyName(company.getName());
        response.setStations(company.getStation());
        return response;
    }

    public List<Company> getCompaniesList(List<String> companiesName) {
        logger.info("CompanyService => getCompaniesList");

        List<Company> companies = new ArrayList<>();
        if (!companiesName.isEmpty()) {

            companiesName.forEach(subCompaniesName -> {
                try {

                    Company companyFoundByName = companyRepository.findByName(subCompaniesName);
                    if (companyFoundByName != null)
                        companies.add(companyFoundByName);

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("CompanyService => getCompaniesList => error on finding company: " + e);
                }
            });
        }
        return companies;
    }
}
