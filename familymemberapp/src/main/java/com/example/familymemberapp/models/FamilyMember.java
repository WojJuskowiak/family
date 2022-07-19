package com.example.familymemberapp.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("family_member")
public class FamilyMember {
    public FamilyMember(Long id, Long familyId, String familyName, String givenName) {
        this.id = id;
        this.familyId = familyId;
        this.familyName = familyName;
        this.givenName = givenName;
    }

    @Id
    private Long id;
    private Long familyId;
    private String givenName;
    private String familyName;
}
