package com.example.tweetservice.controller;

import com.example.tweetservice.model.LikeByTweet;
import com.example.tweetservice.model.TweetByUser;
import com.example.tweetservice.repository.LikeRepository;
import com.example.tweetservice.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private LikeRepository likeRepository;

    // ------------------------------------------------------------------------------------------------------------- //
    //                                            TWEET CONTROLLER                                                    //

    //Potrebno je izmeniti tako da prima korisnicko ime iz tokena
    @PostMapping("/tweets")
    public TweetByUser addTweet(@RequestBody TweetByUser tweet){
        TweetByUser newTweet = new TweetByUser();
        newTweet.setTweetid(UUID.randomUUID());
        newTweet.setText(tweet.getText());
        newTweet.setCreatedtime(new Date());
        newTweet.setUserid(tweet.getUserid());
        tweetRepository.save(newTweet);
        return newTweet;
    }

    @GetMapping("/tweets")
    public List<TweetByUser> getTweets(){

        return tweetRepository.findAll();
    }

    //Potrebno je izmeniti tako da prima korisnicko ime iz tokena
    @GetMapping("/tweetsByUser/{userid}")
    public List<TweetByUser> getTweetsFromOneUser(@PathVariable String userid){
        List<TweetByUser> tweetsByUsers = tweetRepository.findByUserid(userid).orElse(null);
        return tweetsByUsers;
    }

    // ------------------------------------------------------------------------------------------------------------- //
    //                                            LIKE CONTROLLER                                                    //


    @PostMapping("/likes")
    public LikeByTweet addLike(@RequestBody LikeByTweet likeByTweet) {
        LikeByTweet newLikeByTweet = new LikeByTweet();
        newLikeByTweet.setLikeid(UUID.randomUUID());
        newLikeByTweet.setTweetid(likeByTweet.getTweetid());
        newLikeByTweet.setUserid(likeByTweet.getUserid());
        newLikeByTweet.setActive(true);
        likeRepository.save(newLikeByTweet);
        return newLikeByTweet;
    }

}
