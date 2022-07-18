package com.example.familyapp.models.responses;

import com.example.familyapp.models.Family;
import com.example.familyapp.models.FamilyMember;
import com.example.familyapp.models.FamilyMemberList;
import lombok.Data;

import java.util.List;

@Data
public class GetFamilyResponse {
    Family family;

    FamilyMemberResponse[] familyMembers;

    public GetFamilyResponse(Family family, FamilyMemberResponse[] familyMembers) {
        this.family = family;
        this.familyMembers = familyMembers;
    }
}
