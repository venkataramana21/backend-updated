package com.example.MoodTracker.Repository;

import com.example.MoodTracker.Entity.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodRepository extends JpaRepository<Mood,Long> {
    List<Mood> findByUserId(long userId);
}
