package com.example.familyapp.models.responses;

import com.example.familyapp.models.Family;
import lombok.Data;

@Data
public class GetFamilyResponse {
    Family family;

    FamilyMemberResponse[] familyMembers;

    public GetFamilyResponse(Family family, FamilyMemberResponse[] familyMembers) {
        this.family = family;
        this.familyMembers = familyMembers;
    }
}
