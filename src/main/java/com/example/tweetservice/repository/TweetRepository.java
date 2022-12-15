package com.example.tweetservice.repository;

import com.example.tweetservice.model.TweetByUser;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends CassandraRepository<TweetByUser, Integer> {

    Optional<List<TweetByUser>> findByUserid(String userid);


}
