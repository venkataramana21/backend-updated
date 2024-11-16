package com.example.Mental_Wellbeing.Controller;

import com.example.Mental_Wellbeing.Entity.TestScore;
import com.example.Mental_Wellbeing.Repository.TestScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestScoreController {
    @Autowired
    private TestScoreRepository testScoreRepository;
    @PostMapping("/test-scores")
    public ResponseEntity<String> submitTestScore(@RequestBody TestScore testScore) {

            testScoreRepository.save(testScore);

        return ResponseEntity.ok("Test score saved successfully");
    }

    @GetMapping("/testHistory")
    public List<TestScore> getTestHistory(@RequestParam long userId) {
        return testScoreRepository.findByUserId(userId);

    }


}
