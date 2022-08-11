package webService.charging.station.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webService.charging.station.model.CompaniesStationsResponse;
import webService.charging.station.model.CompanyRequest;
import webService.charging.station.model.CompanyResponse;
import webService.charging.station.model.entity.Company;
import webService.charging.station.repository.CompanyRepository;
import webService.charging.station.service.CompanyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/company")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyRepository companyRepository, CompanyService companyService) {
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    @GetMapping(value = "/get_all_info")
    public ResponseEntity<?> getAllCompanyInfo() {
        logger.info("getAllCompanyInfo => getting all companies information.");

        //Using repository interface to search and get all companies.
        List<Company> allCompanies = companyRepository.findAll();
        List<CompanyResponse> companyResponses = new ArrayList<>();

        if (!allCompanies.isEmpty()) {
            logger.info("getAllCompanyInfo => all companies found: returning the result.");

            //getInformation method of company service creates the responses and returns a list of this responses
            companyResponses = companyService.getInformation(allCompanies);
        } else
            logger.info("getAllCompanyInfo => no companies found so returning an empty list.");

        return ResponseEntity.ok(companyResponses);
    }

    @GetMapping(value = "/get_info")
    public ResponseEntity<?> getSingleCompanyInfo(@RequestParam(value = "name") String companyName) {
        logger.info("getSingleCompanyInfo => getting a single company's information");

        //company repository finds an entity with an identifier(company name)
        Company company = companyRepository.findByName(companyName);
        if (company == null) {
            logger.error("getSingleCompanyInfo => no companies found with this company name!");
            return new ResponseEntity<>("no companies found with this company name!", HttpStatus.NOT_FOUND);
        }

        logger.info("getSingleCompanyInfo => returning information response.");
        return ResponseEntity.ok(new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getStation(),
                company.getSubCompany(),
                company.getParentCompany()));
    }

    @GetMapping(value = "/get_all_stations")
    public ResponseEntity<?> getAllStations(@RequestParam("company") String companyName) {
        logger.info("getAllStations => getting all company's and it's sub company's stations from given company name.");

        Company company = companyRepository.findByName(companyName);
        if (company == null) {
            logger.error("getAllStations => no companies found with this company name!");
            return new ResponseEntity<>("no companies found with this company name!", HttpStatus.NOT_FOUND);
        }
        //getCompaniesStations method of company service creates the responses and returns a list of stations
        CompaniesStationsResponse stationsResponse = companyService.getCompaniesStations(company);

        return new ResponseEntity<>(stationsResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteOperation(@PathVariable(value = "id") int companyId) {
        logger.info("company controller => deleteOperation =>  delete entity with given company id.");

        Company company = companyRepository.findById(companyId);
        if (company == null) {
            logger.error("company controller => deleteOperation => no companies found with this company id!");
            return new ResponseEntity<>("no companies found with this id!", HttpStatus.NOT_FOUND);
        }

        //company repository deletes this entity with all it's relations because of its cascade type
        try {
            companyRepository.delete(company);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("company controller => deleteOperation => DB error : error on deleting entity: " + e);
            return new ResponseEntity<>("Error on deleting entity!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("operation deleted successfully");
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateOperation(@RequestBody CompanyRequest request, @PathVariable(value = "id") int companyId) {
        logger.info("company controller => updateOperation =>  update entity with given company id.");

        Company company = companyRepository.findById(companyId);
        if (company == null) {
            logger.error("company controller => updateOperation => no companies found with this company id!");
            return new ResponseEntity<>("no companies found with this id!", HttpStatus.NOT_FOUND);
        }

        if (request.getParentCompany() != null) {
            Company parentCompany = companyRepository.findByName(request.getParentCompany());
            company.setParentCompany(parentCompany != null ? parentCompany : company.getParentCompany());
        }
        if (!request.getSubCompany().isEmpty())
            company.setSubCompany(companyService.getCompaniesList(request.getSubCompany()));

        if (request.getName() != null) company.setName(request.getName());

        //in this method company repository replaces new data
        try {
            companyRepository.save(company);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("company controller => updateOperation => DB error : error on updating entity: " + e);
            return new ResponseEntity<>("Error on updating entity!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("operation updated successfully");
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addOperation(@RequestBody CompanyRequest request) {
        logger.info("company controller => addOperation =>  adding new operation.");

        if (request.getName() == null) {
            logger.info("company controller => addOperation => request body should have at least a name to save into DB.");
            return new ResponseEntity<>("A company must have a name!", HttpStatus.BAD_REQUEST);
        }
        Company companyExistence = companyRepository.findByName(request.getName());
        if (companyExistence != null) {
            logger.error("company controller => addOperation => company already exists!");
            return new ResponseEntity<>("company already exists!", HttpStatus.CONFLICT);
        }

        //creating a new company instance to add into DB
        Company company = new Company(
                request.getName(),
                companyRepository.findByName(request.getParentCompany()),
                companyService.getCompaniesList(request.getSubCompany()));

        //in this method company repository adds new data
        try {
            companyRepository.save(company);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("company controller => addOperation => DB error : error on adding new entity: " + e);
            return new ResponseEntity<>("Error on adding new entity!", HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>("company added successfully", HttpStatus.CREATED);
    }
}
