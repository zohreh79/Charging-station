package webService.charging.station.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import webService.charging.station.model.AroundStationsResponse;
import webService.charging.station.model.StationResponse;
import webService.charging.station.model.entity.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StationService {

    private static final Logger logger = LoggerFactory.getLogger(StationService.class);

    //Each degree of latitude is approximately 69 miles apart. so it is 111.1 km.
    //this is not fully correct for the Earth's polar flattening.
    private static final double latitudeInKm = 111.1;

    //and each degree of longitude is approximately 69.172 miles apart. so it is 111.320 * cos(latitude)
    private static final double longitudeInKm = 111.320;

    public List<StationResponse> getInformation(List<Station> stations) {
        logger.info("StationService => getInformation");

        List<StationResponse> stationResponseList = new ArrayList<>();
        stations.forEach(station -> stationResponseList.add(new StationResponse(
                station.getId(),
                station.getName(),
                station.getLatitude(),
                station.getLongitude(),
                station.getCompany())));

        return stationResponseList;

    }

    public HashMap<String, Double> minAndMaxPosition(double longitude, double latitude, double distance) {

        //deltas of latitude and longitude
        double deltaLat = distance / latitudeInKm;
        double deltaLong = distance / (longitudeInKm * Math.cos(latitude / (180 * Math.PI)));

        HashMap<String, Double> minAndMaxPosition = new HashMap<>();
        minAndMaxPosition.put("minLat", latitude - deltaLat);
        minAndMaxPosition.put("maxLat", latitude + deltaLat);
        minAndMaxPosition.put("minLong", longitude - deltaLong);
        minAndMaxPosition.put("maxLong", longitude + deltaLong);

        return minAndMaxPosition;
    }

    private double distanceCalculator(double point1Lat, double point2Lat, double point1Long, double point2Long) {

        //to calculate distance between two locations we can use HAVERSINE formula which it says:
        //d = 2R⋅sin⁻¹(√[sin²((θ₂ - θ₁)/2) + cos θ₁⋅cos θ₂⋅sin²((φ₂ - φ₁)/2)])
        //R - Earth's radius (R = 6371 km)
        //θ₂, φ₂ – Second point latitude and longitude coordinates
        //θ₁, φ₁ – First point latitude and longitude coordinates
        //d – Distance between them along Earth's surface
        //θ - latitude
        //φ - longitude

        double l1 = point1Lat * Math.PI / 180;
        double l2 = point2Lat * Math.PI / 180;
        double T1 = (point2Lat - point1Lat) * Math.PI / 180;
        double T2 = (point2Long - point1Long) * Math.PI / 180;
        double a = Math.sin(T1 / 2) * Math.sin(T1 / 2) +
                Math.cos(l1) * Math.cos(l2) *
                        Math.sin(T2 / 2) * Math.sin(T2 / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double earthRadius = 6371000;
        return earthRadius * c;
    }

    public List<AroundStationsResponse> getLocations(List<Station> stations, double firstPointLat, double firstPointLong) {

        List<AroundStationsResponse> stationsResponses = new ArrayList<>();
        stations.forEach(station -> {
            double distance = distanceCalculator(firstPointLat, station.getLatitude(), firstPointLong, station.getLongitude());
            stationsResponses.add(new AroundStationsResponse(
                    station.getId(),
                    station.getName(),
                    station.getLatitude(),
                    station.getLongitude(),
                    distance));
        });
        return stationsResponses;
    }
}
