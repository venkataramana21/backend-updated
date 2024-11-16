package com.example.Mental_Wellbeing.Repository;

import com.example.Mental_Wellbeing.Entity.TestScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestScoreRepository extends JpaRepository<TestScore,Long> {
    List<TestScore> findByUserId(long userId);
}
