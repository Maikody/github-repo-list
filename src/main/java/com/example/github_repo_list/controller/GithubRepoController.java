package com.example.github_repo_list.controller;

import com.example.github_repo_list.exception.InvalidAcceptHeaderException;
import com.example.github_repo_list.model.Branch;
import com.example.github_repo_list.model.Repository;
import com.example.github_repo_list.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class GithubRepoController {

    private final GithubService githubService;

    @GetMapping(value = "/repositories/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getRepositories(@PathVariable String username,
                                           @RequestHeader(value = "Accept") String acceptHeader) {
        checkAcceptHeader(acceptHeader);

        List<Repository> repositories = githubService.getRepositories(username)
                .stream()
                .filter(repo -> !repo.isFork())
                .toList();

        for (Repository repo : repositories) {
            List<Branch> branches = githubService.getBranches(username, repo.getName());
            repo.setBranches(branches);
        }

        return ResponseEntity.ok(repositories);
    }

    private void checkAcceptHeader(String acceptHeader) {
        if (!MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader)) {
            throw new InvalidAcceptHeaderException("Invalid Accept header");
        }
    }
}
