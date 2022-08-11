package webService.charging.station.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webService.charging.station.model.AroundStationsResponse;
import webService.charging.station.model.StationRequest;
import webService.charging.station.model.StationResponse;
import webService.charging.station.model.entity.Company;
import webService.charging.station.model.entity.Station;
import webService.charging.station.repository.CompanyRepository;
import webService.charging.station.repository.StationRepository;
import webService.charging.station.service.StationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/station")
public class StationController {

    private static final Logger logger = LoggerFactory.getLogger(StationController.class);

    private final StationRepository stationRepository;
    private final CompanyRepository companyRepository;
    private final StationService stationService;

    @Autowired
    public StationController(StationRepository stationRepository, StationService stationService, CompanyRepository companyRepository) {
        this.stationRepository = stationRepository;
        this.companyRepository = companyRepository;
        this.stationService = stationService;
    }

    @GetMapping(value = "/get_all_info")
    public ResponseEntity<?> getAllStationsInfo() {
        logger.info("getAllStationsInfo => getting all stations information.");

        //Using repository interface to search and get all stations.
        List<Station> allStations = stationRepository.findAll();
        List<StationResponse> stationResponses = new ArrayList<>();

        if (!allStations.isEmpty()) {
            logger.info("getAllStationsInfo => all stations found: returning the result.");

            //getInformation method of station service creates the responses and returns a list of this responses
            stationResponses = stationService.getInformation(allStations);
        } else
            logger.info("getAllStationsInfo => no stations found so returning an empty list.");

        return ResponseEntity.ok(stationResponses);
    }


    @GetMapping(value = "/get_info")
    public ResponseEntity<?> getSingleStationInfo(@RequestParam(value = "name") String stationName) {
        logger.info("getSingleStationInfo => getting a single station's information");

        //station repository finds an entity with an identifier(station name)
        Station station = stationRepository.findByName(stationName);
        if (station == null) {
            logger.error("getSingleStationInfo => no stations found with this station name!");
            return new ResponseEntity<>("no stations found with this station name!", HttpStatus.NOT_FOUND);
        }

        logger.info("getSingleStationInfo => returning information response.");
        return ResponseEntity.ok(new StationResponse(
                station.getId(),
                station.getName(),
                station.getLatitude(),
                station.getLongitude(),
                station.getCompany()));
    }

    @GetMapping(value = "/get_around_stations")
    public ResponseEntity<?> getAroundStations(@RequestParam(value = "location") List<Double> location, @RequestParam(value = "distance") double distance) {
        logger.info("getAroundStations =>  getting stations location from given distance around user's location.");


        if (location.isEmpty() || location.size() < 2) {
            logger.error("getAroundStations => location is empty or it's not a correct location");
            return new ResponseEntity<>("location not found maybe an index is missing", HttpStatus.NOT_FOUND);
        }
        HashMap<String, Double> minAndMaxPositions = stationService.minAndMaxPosition(location.get(0), location.get(1), distance); //(longitude,latitude)
        List<Station> stationsList = stationRepository.findPositionsBetweenMaxAndMin(
                minAndMaxPositions.get("maxLat"),
                minAndMaxPositions.get("minLat"),
                minAndMaxPositions.get("minLong"),
                minAndMaxPositions.get("maxLong"));

        List<AroundStationsResponse> aroundStationsResponses = stationService.getLocations(stationsList, location.get(1), location.get(0));
        return ResponseEntity.ok(aroundStationsResponses);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteOperation(@PathVariable(value = "id") int stationId) {
        logger.info("StationController => deleteOperation =>  delete entity with given station id.");

        Station station = stationRepository.findById(stationId);
        if (station == null) {
            logger.error("StationController => deleteOperation => no stations found with this station id!");
            return new ResponseEntity<>("no stations found with this id!", HttpStatus.NOT_FOUND);
        }

        //station repository deletes this entity with all it's relations because of its cascade type
        try {
            stationRepository.delete(station);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("StationController => deleteOperation => DB error : error on deleting entity: " + e);
            return new ResponseEntity<>("Error on deleting entity!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("operation deleted successfully");
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateOperation(@RequestBody StationRequest stationRequest, @PathVariable(value = "id") int stationId) {
        logger.info("StationController => updateOperation =>  update entity with given station id.");

        Station station = stationRepository.findById(stationId);
        if (station == null) {
            logger.error("StationController => updateOperation => no stations found with this station id!");
            return new ResponseEntity<>("no stations found with this id!", HttpStatus.NOT_FOUND);
        }

        if (stationRequest.getCompanyName() != null) {
            Company company = companyRepository.findByName(stationRequest.getCompanyName());
            station.setCompany(company != null ? company : station.getCompany());
        }
        if (stationRequest.getName() != null) station.setName(stationRequest.getName());
        if (stationRequest.getLatitude() != 0) station.setLatitude(stationRequest.getLatitude());
        if (stationRequest.getLongitude() != 0) station.setLongitude(stationRequest.getLongitude());

        //in this method station repository replaces new data
        try {
            stationRepository.save(station);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("StationController => updateOperation => DB error : error on updating entity: " + e);
            return new ResponseEntity<>("Error on updating entity!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("operation updated successfully");
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addOperation(@RequestBody StationRequest stationRequest) {
        logger.info("StationController => addOperation =>  adding new operation.");

        if (stationRequest.getName() == null) {
            logger.info("StationController => addOperation => request body should have at least a name to save into DB.");
            return new ResponseEntity<>("A station must have a name!", HttpStatus.BAD_REQUEST);
        }
        if (stationRequest.getCompanyName() == null) {
            logger.info("StationController => addOperation => request body should have a company name to save into DB.");
            return new ResponseEntity<>("A station must have a company name!", HttpStatus.BAD_REQUEST);
        }
        Company company = companyRepository.findByName(stationRequest.getCompanyName());
        if (company == null) {
            logger.error("StationController => addOperation => no companies found with this company name!");
            return new ResponseEntity<>("no companies found with this company name!", HttpStatus.NOT_FOUND);
        }

        Station stationExistence = stationRepository.findByName(stationRequest.getName());
        if (stationExistence != null) {
            logger.error("StationController => addOperation => station already exists!");
            return new ResponseEntity<>("station already exists!", HttpStatus.CONFLICT);
        }

        //creating a new station instance to add into DB
        Station station = new Station(
                stationRequest.getName(),
                stationRequest.getLatitude(),
                stationRequest.getLongitude(),
                company);

        //in this method station repository adds new data
        try {
            stationRepository.save(station);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("StationController => addOperation => DB error : error on adding new entity: " + e);
            return new ResponseEntity<>("Error on adding new entity!", HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>("station added successfully", HttpStatus.CREATED);
    }
}
