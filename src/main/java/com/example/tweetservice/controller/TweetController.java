package com.example.tweetservice.controller;

import com.example.tweetservice.exceptions.ResouceNotFoundException;
import com.example.tweetservice.model.Tweet;
import com.example.tweetservice.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @PostMapping("/tweets")
    public Tweet addTweet(@RequestBody Tweet tweet){
        tweetRepository.save(tweet);
        return tweet;
    }

    @GetMapping("/tweets")
    public List<Tweet> getTweets(){

        return tweetRepository.findAll();
    }
}
