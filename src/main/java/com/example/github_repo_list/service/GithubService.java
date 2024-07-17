package com.example.github_repo_list.service;

import com.example.github_repo_list.model.Branch;
import com.example.github_repo_list.model.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {

    private final RestTemplate restTemplate;

    @Value("${github.api.base.url}")
    private String githubApiBaseUrl;

    public List<Repository> getRepositories(String username) {
        String url = UriComponentsBuilder.fromUriString(githubApiBaseUrl + "/users/{username}/repos")
                .buildAndExpand(username)
                .toUriString();

        log.info("Fetching repositories for user: {}", username);
        Repository[] repositories = restTemplate.getForObject(url, Repository[].class);
        if (repositories == null) {
            log.warn("No repositories found for user: {}", username);
            return Collections.emptyList();
        }
        log.info("Found {} repositories for user: {}", repositories.length, username);
        return Arrays.asList(repositories);
    }

    public List<Branch> getBranches(String username, String repoName) {
        String url = UriComponentsBuilder.fromUriString(githubApiBaseUrl + "/repos/{username}/{repo}/branches")
                .buildAndExpand(username, repoName)
                .toUriString();

        log.info("Fetching branches for repository: {}/{}", username, repoName);
        Branch[] branches = restTemplate.getForObject(url, Branch[].class);
        if (branches == null) {
            log.warn("No branches found for repository: {}/{}", username, repoName);
            return Collections.emptyList();
        }
        log.info("Found {} branches for repository: {}/{}", branches.length, username, repoName);
        return Arrays.asList(branches);
    }
}

