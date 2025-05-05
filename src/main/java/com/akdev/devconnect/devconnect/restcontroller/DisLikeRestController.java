package com.akdev.devconnect.devconnect.restcontroller;


import com.akdev.devconnect.devconnect.dto.LikesAndDislikes;
import com.akdev.devconnect.devconnect.dto.LikesAndDislikesDTO;
import com.akdev.devconnect.devconnect.model.DisLikes;
import com.akdev.devconnect.devconnect.repositories.DisLikesRepo;
import com.akdev.devconnect.devconnect.repositories.LikesRepo;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/dislikes")
public class DisLikeRestController {

    @Autowired
    private LikesRepo likesRepo;

    @Autowired
    private DisLikesRepo dislikesRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostsRepo postsRepo;

    /**
     * This method is used to dislike a post.
     * @param likesAndDislikesDTO
     * @return LikesAndDislikes
     */
    @RequestMapping("/dislike")
    public LikesAndDislikes dislike(@RequestBody LikesAndDislikesDTO likesAndDislikesDTO) {

          if (likesRepo.existsByUser_likeAndPost_like(likesAndDislikesDTO.getUser_like_id(), likesAndDislikesDTO.getPost_like_id())) {
            likesRepo.deleteByUser_likeAndPost_like(likesAndDislikesDTO.getUser_like_id(), likesAndDislikesDTO.getPost_like_id());
        }
        LikesAndDislikes likesAndDislikes = new LikesAndDislikes();
        if (dislikesRepo.existsByUser_dislikeAndPost_dislike(likesAndDislikesDTO.getUser_like_id(), likesAndDislikesDTO.getPost_like_id())) {
            likesAndDislikes.setLikesCount(likesRepo.countLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
            likesAndDislikes.setDislikesCount(dislikesRepo.countDisLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
             likesAndDislikes.setPostId(likesAndDislikesDTO.getPost_like_id());
            likesAndDislikes.setUserId(likesAndDislikesDTO.getUser_like_id());
        } else {
            DisLikes likes = new DisLikes();
            likes.setUser_dislike(userRepo.findById(likesAndDislikesDTO.getUser_like_id()).orElse(null));
            likes.setPost_dislike(postsRepo.findById(likesAndDislikesDTO.getPost_like_id()).orElse(null));
            dislikesRepo.save(likes);
            likesAndDislikes.setLikesCount(likesRepo.countLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
            likesAndDislikes.setDislikesCount(dislikesRepo.countDisLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
        }

        return likesAndDislikes;
    }
}
