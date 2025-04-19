package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.dto.LoginRequest;
import com.akdev.devconnect.devconnect.exception.MyLoginException;
import com.akdev.devconnect.devconnect.exception.UserException;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

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
}
