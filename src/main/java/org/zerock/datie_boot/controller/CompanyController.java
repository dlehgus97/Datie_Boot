package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.entity.Company;
import org.zerock.datie_boot.repository.CompanyRepository;

@RestController
@RequestMapping("/api")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @CrossOrigin(origins = "http://localhost:3000") // React 개발 서버의 주소
    @GetMapping("/company")
    public ResponseEntity<Company> getCompanyById(@RequestParam("companyno") int companyno) {
        Company company = companyRepository.findById(companyno).orElse(null);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }
}
