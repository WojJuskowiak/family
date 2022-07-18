package com.example.familyapp.controllers;

import com.example.familyapp.models.requests.CreateFamilyRequest;
import com.example.familyapp.repositories.FamilyRepository;
import com.example.familyapp.services.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class FamilyController {

    private final FamilyService familyService;

    @Autowired
    public FamilyController(FamilyRepository familyRepository, RestTemplateBuilder restTemplateBuilder, JdbcTemplate jdbcTemplate) {
        this.familyService = new FamilyService(familyRepository, restTemplateBuilder, jdbcTemplate);
    }

    @GetMapping("getFamily")
    public ResponseEntity<Object> getFamily(@RequestParam Long id) {
        return this.familyService.getFamily(id);
    }

    @PostMapping("createFamily")
    public ResponseEntity<Object> createFamily(@RequestBody CreateFamilyRequest createFamilyRequest) {
        return this.familyService.createFamily(createFamilyRequest);
    }

}
