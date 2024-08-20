package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.datie_boot.entity.Company;
import org.zerock.datie_boot.repository.CompanyRepository;

@RestController
@RequestMapping("/api")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company")
    public ResponseEntity<Company> getCompanyById(@RequestParam("companyno") int companyno) {
        System.out.println("Received request for company with companyno: " + companyno);
        Company company = companyRepository.findById(companyno).orElse(null);
        if (company == null) {
            System.out.println("Company not found with companyno: " + companyno);
            return ResponseEntity.notFound().build();
        }
        System.out.println("Company found: " + company);
        return ResponseEntity.ok(company);
    }
}
