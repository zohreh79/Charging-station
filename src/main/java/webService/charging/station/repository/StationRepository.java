package webService.charging.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webService.charging.station.model.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
}
