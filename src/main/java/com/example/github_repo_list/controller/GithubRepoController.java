package com.example.github_repo_list.controller;

import com.example.github_repo_list.model.Repository;
import com.example.github_repo_list.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class GithubRepoController {

    private final GithubService githubService;

    @GetMapping(value = "/repositories/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getRepositories(@PathVariable String username) {
        List<Repository> repositories = githubService.getRepositoriesWithBranches(username);
        return ResponseEntity.ok(repositories);
    }

}
