package webService.charging.station.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/company")
public class CompanyController {


    @GetMapping(value = "/get_all_info")
    public ResponseEntity<?> getAllCompanyInfo() {
        return null;
    }


    @GetMapping(value = "/get_info")
    public ResponseEntity<?> getSingleCompanyInfo(@RequestParam(value = "name") String companyName) {
        return null;
    }

    @GetMapping(value = "/get_all_stations")
    public ResponseEntity<?> getAllStations() {
        return null;
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteOperation() {
        return null;
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateOperation() {
        return null;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addOperation() {
        return null;
    }
}
