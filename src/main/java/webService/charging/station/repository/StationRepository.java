package webService.charging.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webService.charging.station.model.entity.Company;
import webService.charging.station.model.entity.Station;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {

    Station findByName(String name);
    List<Station> findByCompany(Company company);
    Station findById(int id);

    @Query(value = "SELECT * FROM Station where" +
            " latitude < ?1 and latitude > ?2 " +
            "and longitude < ?3 and longitude > ?4",
            nativeQuery = true)
    List<Station> findPositionsBetweenMaxAndMin(double maxLat, double minLat, double maxLong, double minLong);
}
