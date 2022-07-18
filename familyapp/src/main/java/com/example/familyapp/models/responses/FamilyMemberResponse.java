package com.example.familyapp.models.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FamilyMemberResponse {
    private Long id;

    private Long familyId;

    private String givenName;

    private String familyName;
}
