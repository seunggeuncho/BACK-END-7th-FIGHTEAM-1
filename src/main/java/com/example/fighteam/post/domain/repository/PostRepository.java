package com.example.fighteam.post.domain.repository;

import com.example.fighteam.post.domain.dto.*;

import java.util.List;

public interface PostRepository {
    Long insertproject(CreatePostDto createPostDto);
    Boolean removepost(Long post_id);

    Boolean insertlanguage(Long post_id, String language);

    Boolean inserttype(Long post_id, String type);

    List<GetBoardResponseDto> findAllBoard(String topic, int page, int count);

    List<String>findlanguage(Long post_id);

    List<String>findcontent(Long post_id);

    GetPostDetailResponseDto findById(Long user_id);

    int countboard(String topic);

    List<GetBoardResponseDto> findAllBoardByLanguage(String topic, int page, int count, String language);

    int countboardByLanguage(String topic, String language);

    void removelanguage(Long postId);

    void removetype(Long postId);

    void removecomment(Long postId);

    void updatepost(CreatePostDto createPostDto, Long post_id);

    List<GetBoardResponseDto> findPostByUserID(Long id);


    void completePost(Long postId);
}
