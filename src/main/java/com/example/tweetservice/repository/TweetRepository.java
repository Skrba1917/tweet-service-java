package com.example.tweetservice.repository;

import com.example.tweetservice.model.Tweet;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface TweetRepository extends CassandraRepository<Tweet, Integer> {


}
