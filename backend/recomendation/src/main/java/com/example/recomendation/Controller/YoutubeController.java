package com.example.recomendation.Controller;

import com.example.recomendation.Service.YoutubeService;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class YoutubeController {

    @Autowired
    YoutubeService youtubeService;
    @GetMapping("/api/videos")
    public List<SearchResult> getVideos(@RequestParam String query) throws IOException {
        return youtubeService.searchVideos(query);
    }
}
