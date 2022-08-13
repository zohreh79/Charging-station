package webService.charging.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import webService.charging.station.model.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByName(String name);
    Company findById(int id);

    @Modifying
    @Query(value = "update sub_company s set s.company_id=? where s.sub_id=? ", nativeQuery = true)
    void updateSubCompany(int CompanyId, int subId);
}
