package com.example.MoodTracker.Controller;

import com.example.MoodTracker.Entity.Mood;
import com.example.MoodTracker.Service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mood")
@CrossOrigin
public class MoodController {

    @Autowired
    MoodService moodService;

    @GetMapping("/entries/{userId}")
    public List<Mood> getAllEnteries(@PathVariable long userId){
       return  moodService.getAllEntries(userId);
    }

    @PostMapping("/save")
    public ResponseEntity<Mood> addMood(@RequestBody Mood mood){
        return moodService.save(mood);
    }

    @DeleteMapping("/{moodId}")
    public void deleteMood(@PathVariable long moodId){
         moodService.delete(moodId);
    }




}
