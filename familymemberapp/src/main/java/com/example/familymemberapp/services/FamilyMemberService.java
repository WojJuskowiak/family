package com.example.familymemberapp.services;

import com.example.familymemberapp.models.FamilyMember;
import com.example.familymemberapp.repositories.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberService {
    private FamilyMemberRepository familyMemberRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setFamilyMemberRepository(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseEntity<Object> searchFamilyMembers(Long familyId) {
        String sqlQuerySelectFamilyMembersById = "SELECT * FROM family_member.family_member WHERE family_id = " + familyId + ';';
        List<FamilyMember> response = this.jdbcTemplate.query(sqlQuerySelectFamilyMembersById, (rs, rowNum) -> new FamilyMember(rs.getLong("id"), rs.getLong("family_id"), rs.getString("family_name"), rs.getString("given_name")));
        if (response.isEmpty()) {
            return new ResponseEntity<>("Family members not found.", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response.toArray(), new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<Object> createFamilyMember(FamilyMember familyMember) {
        return new ResponseEntity<>(this.familyMemberRepository.save(familyMember).getId(), new HttpHeaders(), HttpStatus.CREATED);
    }
}
