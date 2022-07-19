package com.example.familyapp.models;

import com.example.familyapp.utils.CommonValidators;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FamilyMember {
    @JsonIgnore
    private Long id;
    private Long familyId;
    private String givenName;
    private String familyName;
    private int age;
    private boolean isAgeValid() {
        return age >= 0 && age < 150;
    }

    @JsonIgnore
    public FamilyMemberType getFamilyMemberType() {
        if (!isAgeValid()) {
            return FamilyMemberType.NONE;
        } else if (age <= 4) {
            return FamilyMemberType.INFANT;
        } else if (age < 16) {
            return FamilyMemberType.CHILD;
        } else {
            return FamilyMemberType.ADULT;
        }
    }

    public String getInvalidityReason(String familyName) {
        if (familyId == null) {
            return "Field familyId is mandatory.";
        }
        if (givenName == null) {
            return "Field givenName is mandatory.";
        }
        if (this.familyName == null) {
            return "Field familyName is mandatory.";
        }
        if (!CommonValidators.isNameValid(this.familyName)) {
            return "Family name is invalid. The family name should consist of at most 20 characters and the first letter should be capital.";
        }
        if (!this.familyName.equals(familyName)) {
            return "Family name is invalid. The family name of family member should be the same as the family name of the family.";
        }
        if (!CommonValidators.isNameValid(this.givenName)) {
            return "Given name is invalid. The given name should consist of at most 20 characters and the first letter should be capital.";
        }
        if (!isAgeValid()) {
            return "Age is invalid. The age should be a number between 0 and 149 inclusive.";
        }
        return null;
    }
}
