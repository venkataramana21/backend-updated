package com.example.MoodTracker.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long moodId;
    private long userId;
    private int moodScore;
    private String JournalEntry;
    private int sleep;
    private int waterIntake;
    private LocalDate localDate;
}
