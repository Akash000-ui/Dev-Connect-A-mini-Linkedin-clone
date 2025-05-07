package com.akdev.devconnect.devconnect.restcontroller;

import com.akdev.devconnect.devconnect.dto.PostWithCommentsDisLikesAndLikesDTO;
import com.akdev.devconnect.devconnect.dto.SearchFilteredResultDTO;
import com.akdev.devconnect.devconnect.services.FilterUsersService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SearchFilterRestController {

    @Autowired
    private FilterUsersService filterUsersService;

    @GetMapping("/filterByName")
    public List<SearchFilteredResultDTO> getFilteredResults(@RequestParam String term) {
        System.out.println("FilterByName endpoint called");
        System.out.println("Search term: " + term);
        return filterUsersService.getFilteredResults(term);
    }

    @GetMapping("/filterBySkills")
    private List<SearchFilteredResultDTO> getSkillsFilteredResults(@RequestParam String term){
        System.out.println("FilterBySkills endpoint called");
        System.out.println("Search term: " + term);
        return filterUsersService.getSkillsFilteredResult(term);
    }

    @GetMapping("/filterByPosts")
    private List<PostWithCommentsDisLikesAndLikesDTO> getPostsFilteredResults(@RequestParam String term , HttpServletRequest request){
        System.out.println("FilterByPosts endpoint called");
        System.out.println("Search term: " + term);
        Claims claims = (Claims) request.getAttribute("claims");
        return filterUsersService.getPostsFilteredResult(term , claims);
    }
}
