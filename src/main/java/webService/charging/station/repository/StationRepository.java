package webService.charging.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webService.charging.station.model.entity.Station;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {

    Station findByName(String name);

    Station findById(int id);

    @Query(value = "SELECT * FROM Station where latitude between ?1 and ?2 and longitude between ?3 and ?4", nativeQuery = true)
    List<Station> findPositionsBetweenMaxAndMin(double maxLat, double minLat, double maxLong, double minLong);
}
