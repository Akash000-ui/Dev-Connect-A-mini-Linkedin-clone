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
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.*;

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

    public ResponseEntity<Object> addUser(UsersModel user) {
        try {
            String fileUrl = "http://localhost:2525/" + "default.jpeg";
            user.setProfileImageUrl(fileUrl);
            userRepo.save(user);
        } catch (Exception e) {
            throw new UserException("Unable to add user: " + e.getMessage());
        }
        Map<String , Object> response = new HashMap<>();
        response.put( "status" , HttpStatus.CREATED.value());
        response.put("message", "User registered successfully");
        response.put("timestamp", LocalDateTime.now());
        response.put("user", user);

        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @Autowired
    private JwtService jwtService;
    public ResponseEntity<Object> isValid(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        List<UsersModel> users = userRepo.findAll();
        for (UsersModel user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                String token = jwtService.generateToken(user.getName() , user.getEmail() , user);
                System.out.println("Token generated: " + token);
                Map<String , Object> response = new HashMap<>();
                response.put( "status" , HttpStatus.OK.value());
                response.put("message", "User logged in successfully");
                response.put("timestamp", LocalDateTime.now());
                response .put("userId", user.getId());
                response.put("token", token);
                response.put("AuthorName", user.getName());

                return new ResponseEntity<>(response , HttpStatus.OK);
            }
        }
        throw new MyLoginException("Invalid email or password");
    }

    public List<PostWithCommentsDisLikesAndLikesDTO> getPosts(Claims claims) {
        List<Posts> posts = postsRepo.findAll();
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
                postWithCLD.setClientId(claims.get("userId", Long.class));
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

    public List<PostWithCommentsDisLikesAndLikesDTO> getPostByUserId(Long userId){
        System.out.println("______________________________________________________");
        System.out.println("Inside getPostByUserId");
        System.out.println("UserId: " + userId);
        System.out.println("______________________________________________________");

        List<Posts> posts = postsRepo.findAllByUserId(userId);
        for (Posts post : posts) {
            System.out.println("Post ID: " + post.getId());
            System.out.println("Post Title: " + post.getTitle());
            System.out.println("Post Content: " + post.getContent());
            System.out.println("Post Image URL: " + post.getImageUrl());
            System.out.println("Post Author Name: " + post.getAuthorName());
            System.out.println("______________________________________________________");
        }
        System.out.println("Posts: " + posts);
        System.out.println("______________________________________________________");
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
                user.setPassword(randomPassword);
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

    public ResponseEntity<Object> verifyGoogleUser(Map<String , String> token) {
        String idTokenString = token.get("id_token");
        try{
            GoogleIdToken.Payload payload = verifyToken(idTokenString);
            if (payload != null) {
                String email = payload.getEmail();
                UsersModel user = userRepo.findByEmail(email);
                if (user != null) {
                    String token1 = jwtService.generateToken(user.getName() , user.getEmail() , user);
                    System.out.println("Token generated By Google Auth: " + token1);
                    Map<String , Object> response = new HashMap<>();
                    response.put( "status" , HttpStatus.OK.value());
                    response.put("message", "User logged in successfully");
                    response.put("timestamp", LocalDateTime.now());
                    response.put("userId", user.getId());
                    response.put("token", token1);
                    response.put("AuthorName", user.getName());

                    return new ResponseEntity<>(response , HttpStatus.OK);
                } else {
                    throw new UserException("User not found");
                }
            }
        } catch (Exception e) {
            throw new UserException("Unable to verify user: " + e.getMessage());
        }
        Map<String , Object> response = new HashMap<>();
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("message", "Invalid token");
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
