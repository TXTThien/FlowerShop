package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.News;
import org.example.repository.NewsRepository;
import org.example.service.INewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final INewsService newsService;

    @GetMapping("")
    public ResponseEntity<?> getListNews(){
        List<News> news = newsService.findAllEnable();
        if (!news.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("News", news);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No News found");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getNews(@PathVariable("id") int id){
        News news = newsService.findNewsbyNewsID(id);
        if (news != null) {
            return ResponseEntity.ok(news);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No News found");
        }
    }
}