package com.akdev.devconnect.devconnect.restcontroller;


import com.akdev.devconnect.devconnect.dto.LikesAndDislikes;
import com.akdev.devconnect.devconnect.dto.LikesAndDislikesDTO;
import com.akdev.devconnect.devconnect.model.DisLikes;
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

    @Autowired
    private NotificationRestController notificationRestController;

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

            UsersModel usersModel = userRepo.findById(likesAndDislikesDTO.getUser_like_id()).orElse(null);

            if (usersModel == null) {
                throw new IllegalArgumentException("User with ID " + likesAndDislikesDTO.getUser_like_id() + " does not exist.");
            }

            Posts posts = postsRepo.findById(likesAndDislikesDTO.getPost_like_id()).orElse(null);
            if (posts == null) {
                throw new IllegalArgumentException("Post with ID " + likesAndDislikesDTO.getPost_like_id() + " does not exist.");
            }
            likes.setUser_dislike(userRepo.findById(likesAndDislikesDTO.getUser_like_id()).orElse(null));
            likes.setPost_dislike(postsRepo.findById(likesAndDislikesDTO.getPost_like_id()).orElse(null));

            dislikesRepo.save(likes);

            // Send notification
            notificationRestController.sendMessage(usersModel.getId(), posts.getAuthor().getId(), likesAndDislikesDTO.getPost_like_id() ,"Your post has been disliked by " + usersModel.getName());
            likesAndDislikes.setLikesCount(likesRepo.countLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
            likesAndDislikes.setDislikesCount(dislikesRepo.countDisLikesByPostId(likesAndDislikesDTO.getPost_like_id()));
        }

        return likesAndDislikes;
    }
}
