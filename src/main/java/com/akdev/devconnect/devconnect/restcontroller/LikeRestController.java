package com.akdev.devconnect.devconnect.restcontroller;

import com.akdev.devconnect.devconnect.dto.LikesAndDislikes;
import com.akdev.devconnect.devconnect.dto.LikesAndDislikesDTO;
import com.akdev.devconnect.devconnect.model.Likes;
import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.DisLikesRepo;
import com.akdev.devconnect.devconnect.repositories.LikesRepo;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeRestController {

    @Autowired
    private LikesRepo likesRepo;

    @Autowired
    private DisLikesRepo dislikesRepo;

    /**
     * This method is used to like a post.
     * @param likesDTO
     * @return LikesAndDislikes
     */

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostsRepo postsRepo;

    @RequestMapping("/like")
    public LikesAndDislikes like(@RequestBody LikesAndDislikesDTO likesAndDislikesDTO) {
        if (dislikesRepo.existsByUser_dislikeAndPost_dislike(likesAndDislikesDTO.getUser_like_id(), likesAndDislikesDTO.getPost_like_id())) {
            dislikesRepo.deleteByUser_dislikeAndPost_dislike(likesAndDislikesDTO.getUser_like_id(), likesAndDislikesDTO.getPost_like_id());
        }

        LikesAndDislikes likesAndDislikes = new LikesAndDislikes();

        if (likesRepo.existsByUser_likeAndPost_like(likesAndDislikesDTO.getUser_like_id(), likesAndDislikesDTO.getPost_like_id())) {
            likesAndDislikes.setLikesCount(likesRepo.countLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
            likesAndDislikes.setDislikesCount(dislikesRepo.countDisLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
        } else {
            Likes likes = new Likes();
            UsersModel usersModel = userRepo.findById(likesAndDislikesDTO.getUser_like_id()).orElse(null);
            if (usersModel == null) {
                throw new IllegalArgumentException("User with ID " + likesAndDislikesDTO.getUser_like_id() + " does not exist.");
            }
            likes.setUser_like(usersModel);

            // Fetch the Post entity and handle null cases
            Posts post = postsRepo.findById(likesAndDislikesDTO.getPost_like_id()).orElse(null);
            if (post == null) {
                throw new IllegalArgumentException("Post with ID " + likesAndDislikesDTO.getPost_like_id() + " does not exist.");
            }
            likes.setPost_like(post);

            likesRepo.save(likes);
            likesAndDislikes.setLikesCount(likesRepo.countLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
            likesAndDislikes.setDislikesCount(dislikesRepo.countDisLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
            likesAndDislikes.setPostId(likesAndDislikesDTO.getPost_like_id());
        }
        return likesAndDislikes;
    }
}
