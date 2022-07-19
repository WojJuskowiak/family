package com.example.familyapp.models.requests;

import com.example.familyapp.models.Family;
import com.example.familyapp.models.FamilyMember;
import lombok.Data;

@Data
public class CreateFamilyRequest {
    private Family family;
    private FamilyMember[] familyMembers;
}
