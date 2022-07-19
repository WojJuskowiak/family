package com.example.familymemberapp.controllers;

import com.example.familymemberapp.models.FamilyMember;
import com.example.familymemberapp.services.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FamilyMemberController {
    private FamilyMemberService familyMemberService;

    @Autowired
    public void setFamilyMemberService(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    @GetMapping("searchFamilyMembers")
    public ResponseEntity<Object> searchFamilyMembers(@RequestParam Long familyId) {
        return familyMemberService.searchFamilyMembers(familyId);
    }

    @PostMapping("createFamilyMember")
    public ResponseEntity<Object> createFamilyMember(@RequestBody FamilyMember familyMember) {
        return familyMemberService.createFamilyMember(familyMember);
    }
}
