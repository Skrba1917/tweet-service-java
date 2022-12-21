package com.example.tweetservice.repository;

import com.example.tweetservice.model.LikeByTweet;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends CassandraRepository<LikeByTweet, UUID> {

//    @Query(value="SELECT * FROM likebytweet WHERE userid=myuserid AND tweetid=mytweetid;")
    Optional<LikeByTweet> findByUseridAndTweetid(String myuserid, UUID mytweetid);

    Optional<List<LikeByTweet>> findByTweetid(UUID mytweetid);
}
