package com.example.tweetservice.repository;

import com.example.tweetservice.model.LikeByTweet;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends CassandraRepository<LikeByTweet, UUID> {

    @Query(value="select * from likeByTweett where userid=?1 and tweetid=?2;")
    Optional<LikeByTweet> findByUseridAndTweetid(String userid, UUID tweetid);
}
