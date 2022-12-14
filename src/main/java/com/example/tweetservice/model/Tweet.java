package com.example.tweetservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


import java.util.Date;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {

    @PrimaryKey
    private int tweetid;
    private String text;
    private int userid;


}
