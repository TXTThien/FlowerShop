package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.News;
import org.example.entity.enums.Status;
import org.example.repository.NewsRepository;
import org.example.service.INewsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements INewsService {
    private final NewsRepository newsRepository;
    @Override
    public News updateNews(int id, News news) {
        News newNews = newsRepository.findById(id).orElse(null);
        if (newNews!=null){
            newNews.setImage(news.getImage());
            newNews.setTitle(news.getTitle());
            newNews.setStatus(news.getStatus());
            newNews.setContent(news.getContent());
            newNews.setDate(news.getDate());

            return newsRepository.save(newNews);
        }
        return null;
    }

    @Override
    public void deleteNews(int id) {
        News news = newsRepository.findById(id).orElse(null);
        assert news != null;
        news.setStatus(Status.DISABLE);
        newsRepository.save(news);
    }

    @Override
    public List<News> find4NewsEnable() {
        Pageable pageable = PageRequest.of(0, 4);
        List<News> news = newsRepository.findTop3ByStatusOrderByIdDesc(Status.ENABLE, pageable);
        return news;
    }

    @Override
    public List<News> findAllEnable() {
        return newsRepository.findNewsByStatusOrderByDateDesc(Status.ENABLE);
    }

    @Override
    public News findNewsbyNewsID(int id){
        return newsRepository.findNewsByNewsIDAndStatus(id,Status.ENABLE);
    }
}
