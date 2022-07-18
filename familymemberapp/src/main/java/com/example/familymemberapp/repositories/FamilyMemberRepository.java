package com.example.familymemberapp.repositories;

import com.example.familymemberapp.models.FamilyMember;
import org.springframework.data.repository.CrudRepository;

public interface FamilyMemberRepository extends CrudRepository<FamilyMember, Long> {
}
