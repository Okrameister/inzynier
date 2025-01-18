package com.mukesh.controller;

import com.mukesh.models.AppUser;
import com.mukesh.models.News;
import com.mukesh.response.ApiResponse;
import com.mukesh.service.NewsService;
import com.mukesh.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService; // do wyciągania usera z JWT

    @PostMapping("/api/news")
    public ResponseEntity<News> createNews(@RequestHeader("Authorization") String jwt,
                                           @RequestBody News news) throws Exception {
        AppUser reqUser = userService.findUserByJwt(jwt);

        // Przykładowa weryfikacja roli:
        // zakładam, że AppUser ma pole 'role' typu String i tam jest "ADMIN" / "USER" itp.
        if (!"ADMIN".equals(reqUser.getRole())) {
            throw new Exception("Brak uprawnień! Musisz być ADMINEM, by dodać aktualność.");
        }

        News created = newsService.createNews(news, reqUser.getId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/api/news")
    public ResponseEntity<?> getAllNews() {
        return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
    }

    @GetMapping("/api/news/{newsId}")
    public ResponseEntity<?> getNewsById(@PathVariable Integer newsId) throws Exception {
        News found = newsService.getNewsById(newsId);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PutMapping("/api/news/{newsId}")
    public ResponseEntity<News> updateNews(@PathVariable Integer newsId,
                                           @RequestHeader("Authorization") String jwt,
                                           @RequestBody News news) throws Exception {
        AppUser reqUser = userService.findUserByJwt(jwt);

        // Ewentualne sprawdzenie roli:
        if (!"ADMIN".equals(reqUser.getRole())) {
            throw new Exception("Brak uprawnień! Musisz być ADMINEM, by edytować aktualność.");
        }

        News updated = newsService.updateNews(newsId, news, reqUser.getId());
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/api/news/{newsId}")
    public ResponseEntity<ApiResponse> deleteNews(@PathVariable Integer newsId,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        AppUser reqUser = userService.findUserByJwt(jwt);

        if (!"ADMIN".equals(reqUser.getRole())) {
            throw new Exception("Brak uprawnień! Musisz być ADMINEM, by usunąć aktualność.");
        }

        String result = newsService.deleteNews(newsId, reqUser.getId());
        return new ResponseEntity<>(new ApiResponse(result, true), HttpStatus.OK);
    }
}
