package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.datie_boot.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
