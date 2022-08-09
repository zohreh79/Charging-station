package webService.charging.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webService.charging.station.model.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByName(String name);
}
