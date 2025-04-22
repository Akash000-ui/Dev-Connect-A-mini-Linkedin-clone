package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.dto.SearchFilteredResultDTO;
import com.akdev.devconnect.devconnect.model.UsersModel;
import com.akdev.devconnect.devconnect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterUsersService {

    @Autowired
    private UserRepo userRepo;

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
}
