package com.example.EmergencyContact.Repository;

import com.example.EmergencyContact.Entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact,Long> {
//    @Query(value = "select contact_number from emergency_contact where user_id=:user_id",nativeQuery = true)
//String findContactNumberByUserId(long userId);
Optional<String> findContactNumberByUserId(long userId);

    EmergencyContact findByUserId(long userId);
}
