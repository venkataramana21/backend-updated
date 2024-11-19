package com.example.ChatBot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@AllArgsConstructor

public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000) // Adjust length as needed
    @NotNull
    private String prompt;

    @Column(nullable = false, length = 1000) // Adjust length as needed
    @NotNull
    private String response;

    // Default constructor is needed for JPA
    public Conversation() {}

    // Parameterized constructor
    public Conversation(String prompt, String response) {
        this.prompt = prompt;
        this.response = response;
    }
}
