package mister3551.msr.msrserver.service;

import mister3551.msr.msrserver.entity.News;
import mister3551.msr.msrserver.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public ArrayList<News> getNews() {
        return (ArrayList<News>) newsRepository.findAll();
    }

    public News getNewsByTitle(String title) {
        return newsRepository.findByTitle(title);
    }

    public ArrayList<News> getNewsSuggestions(String title) {
        ArrayList<News> filteredNewsList = (ArrayList<News>) newsRepository.findAll().stream()
                .filter(news -> !news.getTitle().equals(title))
                .collect(Collectors.toCollection(ArrayList::new));

        if (filteredNewsList.size() <= 2) {
            return filteredNewsList;
        }

        Collections.shuffle(filteredNewsList, new Random());
        return new ArrayList<>(filteredNewsList.subList(0, 2));
    }
}