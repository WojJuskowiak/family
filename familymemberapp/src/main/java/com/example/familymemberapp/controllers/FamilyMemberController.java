package com.example.familymemberapp.controllers;

import com.example.familymemberapp.models.FamilyMember;
import com.example.familymemberapp.repositories.FamilyMemberRepository;
import com.example.familymemberapp.services.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class FamilyMemberController {
    private final FamilyMemberService familyMemberService;

    @Autowired
    public FamilyMemberController(FamilyMemberRepository familyMemberRepository, JdbcTemplate jdbcTemplate) {
        this.familyMemberService = new FamilyMemberService(familyMemberRepository, jdbcTemplate);
    }

    @GetMapping("searchFamilyMembers")
    public ResponseEntity<Object> searchFamilyMembers(@RequestParam Long familyId) {
        return this.familyMemberService.searchFamilyMembers(familyId);
    }

    @PostMapping("createFamilyMember")
    public ResponseEntity<Object> createFamilyMember(@RequestBody FamilyMember familyMember) {
        return this.familyMemberService.createFamilyMember(familyMember);
    }
}
