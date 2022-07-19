package com.example.familyapp.services;

import com.example.familyapp.models.Family;
import com.example.familyapp.models.FamilyMember;
import com.example.familyapp.models.requests.CreateFamilyRequest;
import com.example.familyapp.models.responses.FamilyMemberResponse;
import com.example.familyapp.models.responses.GetFamilyResponse;
import com.example.familyapp.repositories.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FamilyService {
    private FamilyRepository familyRepository;
    private RestTemplate restTemplate;
    private JdbcTemplate jdbcTemplate;
    @Value("${familymemberapp.url}")
    private String familyMemberAppUrl;

    @Autowired
    public void setFamilyRepository(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private HttpHeaders prepareHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    public ResponseEntity<Object> createFamily(CreateFamilyRequest createFamilyRequest) {
        Long familyId = getNextFamilyId();
        ResponseEntity<Object> createFamilyMembersResponse = this.createFamilyMembers(createFamilyRequest.getFamilyMembers(), familyId, createFamilyRequest.getFamily().getFamilyName());
        if(createFamilyMembersResponse.getStatusCode() != HttpStatus.CREATED) {
            return createFamilyMembersResponse;
        }
        return doCreateFamily(createFamilyRequest.getFamily(), createFamilyRequest.getFamilyMembers());
    }

    private Long getNextFamilyId() {
        String sqlQueryForNextId = "SELECT MAX(id) FROM family.family";
        Long familyId = this.jdbcTemplate.queryForObject(sqlQueryForNextId, Long.class);
        if(familyId == null) {
            return 1L;
        }
        return familyId + 1L;
    }

    private ResponseEntity<Object> doCreateFamily(Family family ,FamilyMember[] familyMembers) {
        String familyInvalidityReason = family.getInvalidityReason(familyMembers);
        if(familyInvalidityReason != null) {
            return new ResponseEntity<>(familyInvalidityReason, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.familyRepository.save(family).getId(), new HttpHeaders(), HttpStatus.CREATED);
    }

    private ResponseEntity<Object> createFamilyMembers(FamilyMember[] familyMembers, Long familyId, String familyName) {
        for(FamilyMember familyMember : familyMembers) {
            familyMember.setFamilyId(familyId);
            ResponseEntity<Object> response = createFamilyMember(familyMember, familyId, familyName);
            if(response.getStatusCode() != HttpStatus.CREATED) {
                return response;
            }
        }
        return new ResponseEntity<>("familyMembersCreated", new HttpHeaders(), HttpStatus.CREATED);
    }

    private ResponseEntity<Object> createFamilyMember(FamilyMember familyMember, Long familyId, String familyName) {
        String invalidityReason = familyMember.getInvalidityReason(familyName);
        if(invalidityReason != null) {
            String reasonInfo = "At " + familyMember + "\nReason:" + invalidityReason;
            return new ResponseEntity<>(reasonInfo, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        HttpHeaders httpHeaders = prepareHttpHeaders();
        Map<String, Object> body = this.prepareCreateFamilyMemberBody(familyMember, familyId);
        String url = familyMemberAppUrl + "/createFamilyMember";
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        return restTemplate.postForEntity(url, httpEntity, Object.class);
    }

    private Map<String, Object> prepareCreateFamilyMemberBody(FamilyMember familyMember, Long familyId) {
        Map<String, Object> body = new HashMap<>();
        body.put("familyId", familyId);
        body.put("familyName", familyMember.getFamilyName());
        body.put("givenName", familyMember.getGivenName());
        return body;
    }

    public ResponseEntity<Object> getFamily(Long id) {
        Optional<Family> family = this.familyRepository.findById(id);
        if(family.isEmpty()) {
            return new ResponseEntity<>("Family not found.", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        String url = this.familyMemberAppUrl + "/searchFamilyMembers?familyId=" + id;
        ResponseEntity<FamilyMemberResponse[]> familyMembersResponse = this.restTemplate.getForEntity(url, FamilyMemberResponse[].class);
        if(familyMembersResponse.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(familyMembersResponse.getBody(), new HttpHeaders(), familyMembersResponse.getStatusCode());
        }
        GetFamilyResponse response = new GetFamilyResponse(family.get(), familyMembersResponse.getBody());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
