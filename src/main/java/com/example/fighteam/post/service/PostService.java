package com.example.fighteam.post.service;

import com.example.fighteam.post.domain.dto.*;
import com.example.fighteam.post.domain.repository.CommentRepository;
import com.example.fighteam.post.domain.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class PostService {

    private final PostRepository postRepository;



    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<GetBoardResponseDto> findAll(String topic, int page){

        List<GetBoardResponseDto> getBoardResponseDto = new ArrayList<>();
        getBoardResponseDto = postRepository.findAllBoard(topic,page, 10);

        for(GetBoardResponseDto g: getBoardResponseDto){
            List<String> language = postRepository.findlanguage(g.getPost_id());
            List<String> type = postRepository.findcontent(g.getPost_id());
            String lan = String.join(", ",language);
            String tp = String.join(", ",type);
            g.setLanguageContent(lan);
            g.setTypeContent(tp);
        }
        return getBoardResponseDto;
    }



    public void createProject(CreatePostDto createPostDto) {
        List<String> language = createPostDto.getLanguageContent();
        List<String> type = createPostDto.getTypeContent();
        Long post_id;
        post_id = postRepository.insertproject(createPostDto);
        for(String L: language){
            postRepository.insertlanguage(post_id, L);
        }
        for (String T: type){
            postRepository.inserttype(post_id, T);
        }

    }


    public Long createProjectWithPay(CreatePostDto createPostDto) {
        List<String> language = createPostDto.getLanguageContent();
        List<String> type = createPostDto.getTypeContent();
        Long post_id;
        post_id = postRepository.insertproject(createPostDto);
        for(String L: language){
            postRepository.insertlanguage(post_id, L);
        }
        for (String T: type){
            postRepository.inserttype(post_id, T);
        }
        return post_id;
    }

    public GetPostDetailResponseDto findByPostId(Long post_id){

        GetPostDetailResponseDto getPostDetailResponseDto = new GetPostDetailResponseDto();
        getPostDetailResponseDto = postRepository.findById(post_id);
        List<String> language = postRepository.findlanguage(getPostDetailResponseDto.getPost_id());
        List<String> type = postRepository.findcontent(getPostDetailResponseDto.getPost_id());
        String lan = String.join(", ",language);
        String tp = String.join(", ",type);
        getPostDetailResponseDto.setLanguageContent(lan);
        getPostDetailResponseDto.setTypeContent(tp);
        return getPostDetailResponseDto;
    }

    public void createStudy(CreateStudyRequestDto createStudyRequestDto) {

    }
    public int CountBoardList(String topic){
        int num = 0;
        num = postRepository.countboard(topic);
        return num;
    }


    public GetPostUpdateResponseDto detailPostForUpdate(Long post_id){
        GetPostDetailResponseDto getPostDetailResponseDto;
        getPostDetailResponseDto = postRepository.findById(post_id);
        List<String> language = postRepository.findlanguage(getPostDetailResponseDto.getPost_id());
        List<String> type = postRepository.findcontent(getPostDetailResponseDto.getPost_id());
        GetPostUpdateResponseDto getPostUpdateResponseDto;
        getPostUpdateResponseDto = GetPostUpdateResponseDto.setGetPostUpdateResponseDtd(getPostDetailResponseDto, language,type);
        return getPostUpdateResponseDto;

    }


    public List<GetBoardResponseDto> findByLanguage(String topic, int page, String language) {

        List<GetBoardResponseDto> getBoardResponseDto = new ArrayList<>();
        getBoardResponseDto = postRepository.findAllBoardByLanguage(topic,page, 10, language);

        for(GetBoardResponseDto g: getBoardResponseDto){
            List<String> languages = postRepository.findlanguage(g.getPost_id());
            List<String> types = postRepository.findcontent(g.getPost_id());
            String lan = String.join(", ",languages);
            String tp = String.join(", ",types);
            g.setLanguageContent(lan);
            g.setTypeContent(tp);
        }
        return getBoardResponseDto;
    }

    public int CountBoardListByLanguage(String topic, String language) {
        int num = 0;
        num = postRepository.countboardByLanguage(topic, language);
        return num;
    }



    public boolean removepost(Long post_id) {
        boolean rs;

        postRepository.removelanguage(post_id);
        postRepository.removetype(post_id);
        postRepository.removecomment(post_id);
        rs = postRepository.removepost(post_id);
        if (rs == true){
            return true;
        }
        return false;
    }

    public void updatepost(CreatePostDto createPostDto, Long id) {
        postRepository.removelanguage(id);
        postRepository.removetype(id);
        List<String> language = createPostDto.getLanguageContent();
        List<String> type = createPostDto.getTypeContent();
        for(String L: language){
            postRepository.insertlanguage(id, L);
        }
        for (String T: type){
            postRepository.inserttype(id, T);
        }
        postRepository.updatepost(createPostDto,id);
    }

    public List<GetBoardResponseDto> findPostbyUserId(Long id) {
        List<GetBoardResponseDto> getBoardResponseDto = new ArrayList<>();
        getBoardResponseDto = postRepository.findPostByUserID(id);

        for(GetBoardResponseDto g: getBoardResponseDto){
            List<String> language = postRepository.findlanguage(g.getPost_id());
            List<String> type = postRepository.findcontent(g.getPost_id());
            String lan = String.join(", ",language);
            String tp = String.join(", ",type);
            g.setLanguageContent(lan);
            g.setTypeContent(tp);
        }
        return getBoardResponseDto;
    }


    public void completepost(Long postId) {
        postRepository.completePost(postId);
    }
}
