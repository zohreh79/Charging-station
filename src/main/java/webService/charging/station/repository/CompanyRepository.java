package webService.charging.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import webService.charging.station.model.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByName(String name);
    Company findById(int id);
}
