package com.example.tweetservice.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
public class LikeByTweet {

    @PrimaryKey
    private UUID likeid;
    private UUID tweetid;

    private String userid;
    private boolean active;

    public LikeByTweet(UUID likeid, UUID tweetid, String userid, boolean active) {
        this.likeid = likeid;
        this.tweetid = tweetid;
        this.userid = userid;
        this.active = active;
    }

    public LikeByTweet(){}

    public UUID getLikeid() {
        return likeid;
    }

    public void setLikeid(UUID likeid) {
        this.likeid = likeid;
    }

    public UUID getTweetid() {
        return tweetid;
    }

    public void setTweetid(UUID tweetid) {
        this.tweetid = tweetid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
