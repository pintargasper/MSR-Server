package mister3551.msr.msrserver.controller;

import mister3551.msr.msrserver.entity.News;
import mister3551.msr.msrserver.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/get-news")
    public ArrayList<News> getNews() {
        return newsService.getNews();
    }

    @GetMapping("/news/{title}")
    public News getNewsByTitle(@PathVariable("title") String title) {
        return newsService.getNewsByTitle(title);
    }

    @GetMapping("/news/suggestions/{title}")
    public ArrayList<News> getNewsSuggestions(@PathVariable("title") String title) {
        return newsService.getNewsSuggestions(title);
    }
}