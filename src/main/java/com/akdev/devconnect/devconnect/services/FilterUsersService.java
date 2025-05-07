package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.dto.CommentDTO;
import com.akdev.devconnect.devconnect.dto.PostWithCommentsDisLikesAndLikesDTO;
import com.akdev.devconnect.devconnect.dto.SearchFilteredResultDTO;
import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.DisLikesRepo;
import com.akdev.devconnect.devconnect.repositories.LikesRepo;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterUsersService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostsRepo postsRepo;

    @Autowired
    private LikesRepo likesRepo;

    @Autowired
    private DisLikesRepo dislikesRepo;

    public List<SearchFilteredResultDTO> getFilteredResults(String searchTerm) {
        List<UsersModel> users = userRepo.findByNameContainingIgnoreCase(searchTerm);
        return users.stream()
                .map(user -> new SearchFilteredResultDTO(user.getId(), user.getName(), user.getBio(),user.getProfileImageUrl()))
                .toList();
    }

    public List<SearchFilteredResultDTO> getSkillsFilteredResult(String term){
        List<UsersModel> users = userRepo.searchBySkillIgnoreCase(term);
        return users.stream()
                .map(user -> new SearchFilteredResultDTO(user.getId(), user.getName(), user.getBio(),user.getProfileImageUrl()))
                .toList();
    }

    public List<PostWithCommentsDisLikesAndLikesDTO> getPostsFilteredResult(String term , Claims claims) {

        List<Posts> posts = postsRepo.findByTitleContainingIgnoreCase(term);
        List<PostWithCommentsDisLikesAndLikesDTO> postsWithCLD = new ArrayList<>();
        try {
            for (Posts post : posts) {
                PostWithCommentsDisLikesAndLikesDTO postWithCLD = new PostWithCommentsDisLikesAndLikesDTO();
                postWithCLD.setPostId(post.getId());
                postWithCLD.setTitle(post.getTitle());
                postWithCLD.setContent(post.getContent());
                postWithCLD.setImageUrl(post.getImageUrl());
                postWithCLD.setAuthorName(post.getAuthorName());
                postWithCLD.setUserId(post.getAuthor().getId());
                //postWithCLD.setClientId(claims.get("userId", Long.class));
                Long likesCount = likesRepo.countLikesByPostId(post.getId());
                Long dislikesCount = dislikesRepo.countDisLikesByPostId(post.getId());

                postWithCLD.setLikesCount(likesCount);
                postWithCLD.setDislikesCount(dislikesCount);

                List<CommentDTO> comments = post.getComments().stream()
                        .map(comments1 -> new CommentDTO(comments1.getId() , comments1.getContent() , comments1.getAuthorName()))
                        .toList();
                postWithCLD.setComments(comments);

                postsWithCLD.add(postWithCLD);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch posts: " + e.getMessage());
        }

        return postsWithCLD;

    }
}
