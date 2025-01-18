package com.mukesh.service;

import com.mukesh.models.AppUser;
import com.mukesh.models.News;
import com.mukesh.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserService userService; // zakładam, że już istnieje i pozwala pobrać użytkownika po JWT

    public News createNews(News news, Integer userId) throws Exception {
        AppUser user = userService.findUserById(userId);
        if (user == null) {
            throw new Exception("Użytkownik nie istnieje!");
        }
        news.setUser(user);
        return newsRepository.save(news);
    }

    public List<News> getAllNews() {
        return newsRepository.findAllByOrderByCreatedAtDesc();
    }

    public News getNewsById(Integer newsId) throws Exception {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new Exception("Nie znaleziono news o ID " + newsId));
    }

    public News updateNews(Integer newsId, News updatedNews, Integer userId) throws Exception {
        News existingNews = getNewsById(newsId);

        // Jeżeli chcesz sprawdzać, czy autor się zgadza lub czy user to ADMIN, możesz to tu dodać
        // ewentualnie: if (!existingNews.getUser().getId().equals(userId)) ...

        existingNews.setTitle(updatedNews.getTitle());
        existingNews.setContent(updatedNews.getContent());

        return newsRepository.save(existingNews);
    }

    public String deleteNews(Integer newsId, Integer userId) throws Exception {
        News existingNews = getNewsById(newsId);
        // sprawdzenie uprawnień
        newsRepository.delete(existingNews);
        return "Pomyślnie usunięto news o ID: " + newsId;
    }
}
