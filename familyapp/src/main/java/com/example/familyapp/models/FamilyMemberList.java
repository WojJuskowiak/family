package com.example.familyapp.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FamilyMemberList {
    private List<FamilyMember> familyMembers;

    public FamilyMemberList() {
        this.familyMembers = new ArrayList<>();
    }
}
