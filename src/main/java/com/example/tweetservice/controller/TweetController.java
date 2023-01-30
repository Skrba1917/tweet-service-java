package com.example.tweetservice.controller;


import com.example.tweetservice.model.LikeByTweet;
import com.example.tweetservice.model.TweetByUser;
import com.example.tweetservice.repository.LikeRepository;
import com.example.tweetservice.repository.TweetRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
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
    @PreAuthorize("hasAnyRole('ROLE_BusinessUser','ROLE_User')")
    @PostMapping("/tweets")
    public TweetByUser addTweet(@Valid Authentication auth, @RequestBody TweetByUser tweet){
    	UserDetails userDetails = (UserDetails)auth.getPrincipal();
  	  	System.out.println(userDetails.getUsername());
            if(tweet.getText().contains(";")){
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, " Tweet ne moze da sadrzi ;");
            }else {
                TweetByUser newTweet = new TweetByUser();
                newTweet.setTweetid(UUID.randomUUID());
                newTweet.setText(tweet.getText());
                newTweet.setCreatedtime(new Date());
                newTweet.setUserid(userDetails.getUsername());
                tweetRepository.save(newTweet);
                return newTweet;
            }
    }
    @GetMapping("/tweets")
    public List<TweetByUser> getTweets(){

        return tweetRepository.findAll();
    }

    //Potrebno je izmeniti tako da prima korisnicko ime iz tokena
    @PreAuthorize("hasAnyRole('ROLE_BusinessUser','ROLE_User')")
    @GetMapping("/tweetsByUser/{userid}")
    public List<TweetByUser> getTweetsFromOneUser(@PathVariable String userid){
        List<TweetByUser> tweetsByUsers = tweetRepository.findByUserid(userid).orElse(null);
        return tweetsByUsers;
    }

    // ------------------------------------------------------------------------------------------------------------- //
    //                                            LIKE CONTROLLER                                                    //

    @PreAuthorize("hasAnyRole('ROLE_BusinessUser','ROLE_User')")
    @PostMapping("/likes")
    public ResponseEntity addLike(@RequestBody LikeByTweet likeByTweet) {

        System.out.println(likeByTweet.getUserid());
        System.out.println(likeByTweet.getTweetid());

        LikeByTweet mojlajk = likeRepository.findByUseridAndTweetid(likeByTweet.getUserid(),likeByTweet.getTweetid()).orElse(null);

        if(mojlajk ==null){
            LikeByTweet newLikeByTweet = new LikeByTweet();
            newLikeByTweet.setLikeid(UUID.randomUUID());
            newLikeByTweet.setTweetid(likeByTweet.getTweetid());
            newLikeByTweet.setUserid(likeByTweet.getUserid());
            newLikeByTweet.setActive(true);
            likeRepository.save(newLikeByTweet);
            return new ResponseEntity(newLikeByTweet, HttpStatus.CREATED);
        }if (!mojlajk.isActive()){
            mojlajk.setActive(true);
            likeRepository.save(mojlajk);
            return new ResponseEntity(mojlajk,HttpStatus.CREATED);
        }
        mojlajk.setActive(false);
        likeRepository.save(mojlajk);
        return new ResponseEntity(mojlajk,HttpStatus.CREATED);



    }

    @PreAuthorize("hasAnyRole('ROLE_BusinessUser','ROLE_User')")
    @GetMapping("likes/{tweetid}")
    public ResponseEntity findAllLikesForTweet(@PathVariable("tweetid") String tweetid){
        List<LikeByTweet> allLikes= likeRepository.findByTweetid(UUID.fromString(tweetid)).orElse(null);
        return new ResponseEntity(allLikes,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_BusinessUser','ROLE_User')")
    @GetMapping("likes/names/{tweetid}")
    public ResponseEntity findAllUsersWhoLikedTweet(@PathVariable("tweetid") String tweetid){
        List<LikeByTweet> allLikes= likeRepository.findByTweetid(UUID.fromString(tweetid)).orElse(null);
        List<String> allNames = new ArrayList<>();
        for(LikeByTweet onelike : allLikes){
            if (onelike.isActive()) {
                allNames.add(onelike.getUserid());
            }
        }
        return new ResponseEntity(allNames,HttpStatus.OK);
    }

  
}
