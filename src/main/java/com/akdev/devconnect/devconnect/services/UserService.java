package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.dto.CommentDTO;
import com.akdev.devconnect.devconnect.dto.LoginRequest;
import com.akdev.devconnect.devconnect.dto.OAuthRegisterDTO;
import com.akdev.devconnect.devconnect.dto.PostWithCommentsDisLikesAndLikesDTO;
import com.akdev.devconnect.devconnect.exception.MyLoginException;
import com.akdev.devconnect.devconnect.exception.UserException;
import com.akdev.devconnect.devconnect.model.Posts;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.DisLikesRepo;
import com.akdev.devconnect.devconnect.repositories.LikesRepo;
import com.akdev.devconnect.devconnect.repositories.PostsRepo;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostsRepo postsRepo;

    @Autowired
    private LikesRepo likesRepo;

    @Autowired
    private DisLikesRepo dislikesRepo;

    public void addUser(UsersModel user) {
        try {
            userRepo.save(user);
        } catch (Exception e) {
            throw new UserException("Unable to add user: " + e.getMessage());
        }
    }

    public void isValid(LoginRequest loginRequest, HttpSession session) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        List<UsersModel> users = userRepo.findAll();
        for (UsersModel user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                return;
            }
        }
        throw new MyLoginException("Invalid email or password");
    }

    public List<PostWithCommentsDisLikesAndLikesDTO> getPosts() {
        List<Posts> posts = postsRepo.findAll();
        List<PostWithCommentsDisLikesAndLikesDTO> postsWithCLD = new ArrayList<>();
        for (Posts post : posts) {
            PostWithCommentsDisLikesAndLikesDTO postWithCLD = new PostWithCommentsDisLikesAndLikesDTO();
            postWithCLD.setPostId(post.getId());
            postWithCLD.setTitle(post.getTitle());
            postWithCLD.setContent(post.getContent());
            postWithCLD.setImageUrl(post.getImageUrl());
            postWithCLD.setAuthorName(post.getAuthorName());

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

        return postsWithCLD;

    }


    public void registerUserWithGoogle(OAuthRegisterDTO oAuthRegisterDTO) {

        String idTokenString = oAuthRegisterDTO.getToken();
        try {
            GoogleIdToken.Payload payload = verifyToken(idTokenString);
            if (payload != null) {
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String bio = oAuthRegisterDTO.getBio();
                List<String> skills = oAuthRegisterDTO.getSkills();
                UsersModel user = new UsersModel();
                String randomPassword = UUID.randomUUID().toString(); // for Java
                user.setEmail(email);
                user.setName(name);
                user.setProfileImageUrl(pictureUrl);
                user.setBio(bio);
                user.setSkills(skills);
                user.setPassword(randomPassword); // Set a random password
                userRepo.save(user);
            }
        } catch (Exception e) {
            throw new UserException("Unable to register user with Google: " + e.getMessage());
        }
    }

    private GoogleIdToken.Payload verifyToken(String idTokenString) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("96934569111-4gg4q9mkhei39id5bvm830b6e5esq6pg.apps.googleusercontent.com")) // 👈 Replace with your client ID
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        return idToken != null ? idToken.getPayload() : null;
    }
}
