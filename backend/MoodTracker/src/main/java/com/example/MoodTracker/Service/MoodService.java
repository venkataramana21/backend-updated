package com.example.MoodTracker.Service;

import com.example.MoodTracker.Entity.Mood;
import com.example.MoodTracker.Repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoodService {

    @Autowired
    MoodRepository moodRepository;

    public List<Mood> getAllEntries(long userId) {
        return moodRepository.findByUserId(userId);
    }

    public ResponseEntity<Mood> save(Mood mood) {
        Mood m=moodRepository.save(mood);
        return new ResponseEntity<>(m, HttpStatus.CREATED);
    }

    public void delete(long moodId) {
        moodRepository.deleteById(moodId);
    }
}
