package com.example.ChatBot.Service;


import com.example.ChatBot.Entity.Conversation;
import com.example.ChatBot.Repository.ConversationRepository;
import org.apache.logging.log4j.util.Strings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIService {

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    private static final String GEMINI_MODEL = "gemini-1.5-flash";
    private static final String API_KEY = "";

    @Autowired
    private ConversationRepository conversationRepository;

    public String chat(String prompt) {
        // Retrieve conversation history from the database
        List<Conversation> history = conversationRepository.findAll();
        StringBuilder conversationHistory = new StringBuilder();
        for (Conversation entry : history) {
            conversationHistory.append(entry.getPrompt()).append("\n").append(entry.getResponse()).append("\n");
        }

        // Adding previous conversation history
        String fullPrompt = prompt;
        if (!Strings.isBlank(String.valueOf(conversationHistory))) {
            fullPrompt = "[Context]" + conversationHistory + " [Content] " + prompt;
        }

        // Prepare headers and request entity
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        fullPrompt = getPromptBody(fullPrompt);
        HttpEntity<String> requestEntity = new HttpEntity<>(fullPrompt, headers);

        // Perform HTTP POST request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://generativelanguage.googleapis.com/v1beta/models/" + GEMINI_MODEL + ":generateContent?key=" + API_KEY,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        // Handle the response based on the status code
        if (statusCode == HttpStatus.OK) {
            String responseText = responseEntity.getBody();
            try {
                responseText = parseGeminiResponse(responseText);
                // Save conversation history to the database
                Conversation conversation = new Conversation(prompt, responseText);
                conversationRepository.save(conversation);
            } catch (Exception e) {
                logger.error("Error in parsing", e);
            }
            return responseText; // Return the fetched summary response
        } else {
            throw new RuntimeException("API request failed with status code: " + statusCode + " and response: " + responseEntity.getBody());
        }
    }

    public String getPromptBody(String prompt) {
        // Create prompt for generating summary in document language
        JSONObject promptJson = new JSONObject();

        // Array to contain all the content-related data, including the text and role
        JSONArray contentsArray = new JSONArray();
        JSONObject contentsObject = new JSONObject();
        contentsObject.put("role", "user");

        // Array to hold the specific parts (or sections) of the user's input text
        JSONArray partsArray = new JSONArray();
        JSONObject partsObject = new JSONObject();
        partsObject.put("text", prompt);
        partsArray.add(partsObject);
        contentsObject.put("parts", partsArray);

        contentsArray.add(contentsObject);
        promptJson.put("contents", contentsArray);

        // Array to hold various safety setting objects to ensure the content is safe and appropriate
        JSONArray safetySettingsArray = new JSONArray();
        addSafetySetting(safetySettingsArray, "HARM_CATEGORY_HATE_SPEECH");
        addSafetySetting(safetySettingsArray, "HARM_CATEGORY_DANGEROUS_CONTENT");
        addSafetySetting(safetySettingsArray, "HARM_CATEGORY_SEXUALLY_EXPLICIT");
        addSafetySetting(safetySettingsArray, "HARM_CATEGORY_HARASSMENT");

        promptJson.put("safetySettings", safetySettingsArray);

        // Creating and setting generation configuration parameters such as temperature and topP
        JSONObject parametersJson = new JSONObject();
        parametersJson.put("temperature", 0.5);
        parametersJson.put("topP", 0.99);
        promptJson.put("generationConfig", parametersJson);

        // Convert the JSON object to a JSON string
        return promptJson.toJSONString();
    }

    private void addSafetySetting(JSONArray safetySettingsArray, String category) {
        JSONObject safetySetting = new JSONObject();
        safetySetting.put("category", category);
        safetySetting.put("threshold", "BLOCK_ONLY_HIGH");
        safetySettingsArray.add(safetySetting);
    }

    public String parseGeminiResponse(String jsonResponse) throws IOException, ParseException {
        // Parse the JSON string
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonResponse);

        // Get the "candidates" array
        JSONArray candidatesArray = (JSONArray) jsonObject.get("candidates");
        if (candidatesArray == null || candidatesArray.isEmpty()) {
            return "No response from AI";
        }

        // Assuming there's only one candidate (index 0), extract its content
        JSONObject candidateObject = (JSONObject) candidatesArray.get(0);
        JSONObject contentObject = (JSONObject) candidateObject.get("content");

        // Get the "parts" array within the content
        JSONArray partsArray = (JSONArray) contentObject.get("parts");
        if (partsArray == null || partsArray.isEmpty()) {
            return "No content available";
        }

        // Extract all parts and join them into a single response
        String responseText = (String) partsArray.stream()
                .map(part -> (String) ((JSONObject) part).get("text"))
                .collect(Collectors.joining("\n"));

        // Clean up unwanted stars or characters if present
        responseText = responseText.replaceAll("\\*+", "").trim();

        return responseText;
    }
}

