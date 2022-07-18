package com.example.familyapp.models;

import com.example.familyapp.utils.CommonValidators;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("family")
public class Family {
    @Id
    private Long id;

    private String familyName;

    private int nrOfInfants;

    private int nrOfChildren;

    private int nrOfAdults;

    @JsonIgnore
    public String getInvalidityReason(FamilyMember[] familyMembers) {
        if(familyName == null) {
            return "Field familyName is mandatory.";
        }
        if(!CommonValidators.isNameValid(familyName)) {
            return "Family name is invalid. The family name should consist of at most 20 characters and the first letter should be capital.";
        }
        if (this.nrOfInfants < 0) {
            return "Number of infants has to be greater or equal to 0.";
        }
        if (this.nrOfChildren < 0) {
            return "Number of children has to be greater or equal to 0.";
        }
        if (this.nrOfAdults < 0) {
            return "Number of adults has to be greater or equal to 0.";
        }
        int nrOfInfants = 0;
        int nrOfChildren = 0;
        int nrOfAdults = 0;
        for (FamilyMember familyMember : familyMembers) {
            FamilyMemberType familyMemberType = familyMember.getFamilyMemberType();
            switch (familyMemberType) {
                case NONE -> {
                    return "Family member age is not between 0 and 149.";
                }
                case INFANT -> nrOfInfants++;
                case CHILD -> nrOfChildren++;
                case ADULT -> nrOfAdults++;
            }
        }
        if(this.nrOfInfants != nrOfInfants) {
            return "Number of infants is invalid. Infants are family members of age 0-4.";
        }
        if(this.nrOfChildren != nrOfChildren) {
            return "Number of children is invalid. Children are family members of age 5-15";
        }
        if(this.nrOfAdults != nrOfAdults) {
            return "Number of adults is invalid. Adults are family members of age greater or equal to 16.";
        }
        return null;
    }
}
