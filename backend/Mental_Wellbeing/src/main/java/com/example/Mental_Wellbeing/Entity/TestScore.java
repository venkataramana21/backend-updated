package com.example.Mental_Wellbeing.Entity;
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
public class TestScore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long testId;
    private LocalDate date;
    private long userId;  // Optional: If you want to store the user's identity
    private int score;
    private String depressionLevel;
}
