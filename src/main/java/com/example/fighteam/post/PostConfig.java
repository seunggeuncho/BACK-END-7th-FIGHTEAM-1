package com.example.fighteam.post;

import com.example.fighteam.post.domain.repository.CommentJdbc;
import com.example.fighteam.post.domain.repository.CommentRepository;
import com.example.fighteam.post.domain.repository.PostJdbc;
import com.example.fighteam.post.domain.repository.PostRepository;
import com.example.fighteam.post.service.CommentService;
import com.example.fighteam.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostConfig {


    private DataSource dataSource;

    @Autowired
    public PostConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PostService postService(){return new PostService(postRepository());}

    @Bean
    public PostRepository postRepository() {
        return new PostJdbc(dataSource);
    }

    @Bean
    public CommentService commentService(){return new CommentService(commentRepository());}

    @Bean
    public CommentRepository commentRepository(){return new CommentJdbc(dataSource);}
}
