package com.akdev.devconnect.devconnect.restcontroller;

import com.akdev.devconnect.devconnect.dto.LikesAndDislikes;
import com.akdev.devconnect.devconnect.dto.LikesDTO;
import com.akdev.devconnect.devconnect.model.DisLikes;
import com.akdev.devconnect.devconnect.model.Likes;
import com.akdev.devconnect.devconnect.model.Posts;
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
    public LikesAndDislikes like(@RequestBody LikesDTO likesDTO) {
        if (dislikesRepo.existsByUser_dislikeAndPost_dislike(likesDTO.getUser_like_id(), likesDTO.getPost_like_id())) {
            dislikesRepo.deleteByUser_dislikeAndPost_dislike(likesDTO.getUser_like_id(), likesDTO.getPost_like_id());
        }

        LikesAndDislikes likesAndDislikes = new LikesAndDislikes();

        if (likesRepo.existsByUser_likeAndPost_like(likesDTO.getUser_like_id(), likesDTO.getPost_like_id())) {
            likesAndDislikes.setLikesCount(likesRepo.countLikesByPostId(likesDTO.getPost_like_id()));
            likesAndDislikes.setDislikesCount(dislikesRepo.countDisLikesByPostId(likesDTO.getPost_like_id()));
        } else {
            Likes likes = new Likes();
            likes.setUser_like(userRepo.findById(likesDTO.getUser_like_id()).orElse(null));

            // Fetch the Post entity and handle null cases
            Posts post = postsRepo.findById(likesDTO.getPost_like_id()).orElse(null);
            if (post == null) {
                throw new IllegalArgumentException("Post with ID " + likesDTO.getPost_like_id() + " does not exist.");
            }
            likes.setPost_like(post);

            likesRepo.save(likes);
            likesAndDislikes.setLikesCount(likesRepo.countLikesByPostId(likesDTO.getPost_like_id()));
            likesAndDislikes.setDislikesCount(dislikesRepo.countDisLikesByPostId(likesDTO.getPost_like_id()));
        }
        return likesAndDislikes;
    }

    /**
     * This method is used to dislike a post.
     * @param likesDTO
     * @return LikesAndDislikes
     */
    @RequestMapping("/dislike")
    public LikesAndDislikes dislike(@RequestBody LikesDTO likesDTO) {
        if (likesRepo.existsByUser_likeAndPost_like(likesDTO.getUser_like_id(), likesDTO.getPost_like_id())) {
            likesRepo.deleteByUser_likeAndPost_like(likesDTO.getUser_like_id(), likesDTO.getPost_like_id());
        }
        LikesAndDislikes likesAndDislikes = new LikesAndDislikes();
        if (dislikesRepo.existsByUser_dislikeAndPost_dislike(likesDTO.getUser_like_id(), likesDTO.getPost_like_id())) {
            likesAndDislikes.setLikesCount(likesRepo.countLikesByPostId(likesDTO.getPost_like_id()));
            likesAndDislikes.setDislikesCount(dislikesRepo.countDisLikesByPostId(likesDTO.getPost_like_id()));
        } else {
            DisLikes likes = new DisLikes();
            likes.setUser_dislike(userRepo.findById(likesDTO.getUser_like_id()).orElse(null));
            likes.setPost_dislike(postsRepo.findById(likesDTO.getPost_like_id()).orElse(null));
            dislikesRepo.save(likes);
            likesAndDislikes.setLikesCount(likesRepo.countLikesByPostId(likesDTO.getPost_like_id()));
            likesAndDislikes.setDislikesCount(dislikesRepo.countDisLikesByPostId(likesDTO.getPost_like_id()));
        }
        return likesAndDislikes;
    }
}
