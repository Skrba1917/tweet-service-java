package com.example.tweetservice.repository;

import com.example.tweetservice.model.LikeByTweet;
import com.example.tweetservice.model.TweetByUser;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TweetRepository extends CassandraRepository<TweetByUser, Integer> {

    Optional<List<TweetByUser>> findByUserid(String userid);



}
