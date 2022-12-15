package com.example.tweetservice.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


import java.util.Date;
import java.util.UUID;

@Table
public class TweetByUser {

    @PrimaryKey
    private UUID tweetid;
    private String text;
    private String userid;
    private Date createdtime;


    public UUID getTweetid() {
        return tweetid;
    }

    public void setTweetid(UUID tweetid) {
        this.tweetid = tweetid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public TweetByUser(UUID tweetid, String text, String userid, Date createdtime) {
        this.tweetid = tweetid;
        this.text = text;
        this.userid = userid;
        this.createdtime = createdtime;
    }

    public TweetByUser() {
    }
}
