package webService.charging.station.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/station")
public class StationController {

    @GetMapping(value = "/get_all_info")
    public ResponseEntity<?> getAllStationsInfo(){
        return null;
    }


    @GetMapping(value = "/get_info")
    public ResponseEntity<?> getSingleStationInfo(@RequestParam(value = "name") String stationName){
        return null;
    }

    @GetMapping(value = "/get_around_stations")
    public ResponseEntity<?> getAroundStations(){
        return null;
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteOperation(){
        return null;
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateOperation(){
        return null;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addOperation(){
        return null;
    }
}
