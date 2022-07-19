package com.example.familyapp.controllers;

import com.example.familyapp.models.requests.CreateFamilyRequest;
import com.example.familyapp.services.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FamilyController {
    private FamilyService familyService;
    @Autowired
    public void setFamilyService(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping("getFamily")
    public ResponseEntity<Object> getFamily(@RequestParam Long id) {
        return familyService.getFamily(id);
    }

    @PostMapping("createFamily")
    public ResponseEntity<Object> createFamily(@RequestBody CreateFamilyRequest createFamilyRequest) {
        return familyService.createFamily(createFamilyRequest);
    }
}
