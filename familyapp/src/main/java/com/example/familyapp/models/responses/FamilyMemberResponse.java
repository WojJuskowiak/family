package com.example.familyapp.models.responses;

import lombok.Data;

@Data
public class FamilyMemberResponse {
    private Long id;
    private Long familyId;
    private String givenName;
    private String familyName;
}
