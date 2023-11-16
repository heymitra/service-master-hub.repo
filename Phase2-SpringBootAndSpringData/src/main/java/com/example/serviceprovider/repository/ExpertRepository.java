package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    @Query("SELECT e FROM Expert e")
    List<Expert> findAllExperts();

    Optional<Expert> findByEmail(String email);
}
