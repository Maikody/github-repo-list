package com.example.github_repo_list.client;

import com.example.github_repo_list.exception.GithubClientException;
import com.example.github_repo_list.model.Branch;
import com.example.github_repo_list.model.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubClient {

    private final RestTemplate restTemplate;

    @Value("${github.api.base.url}")
    private String githubApiBaseUrl;

    public List<Repository> getRepositories(String username) {
        String url = UriComponentsBuilder.fromUriString(githubApiBaseUrl + "/users/{username}/repos")
                .buildAndExpand(username)
                .toUriString();
        log.info("Fetching repositories for user: {}", username);
        try {
            Repository[] repositories = restTemplate.getForObject(url, Repository[].class);
            return Optional.ofNullable(repositories)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientResponseException e) {
            log.error("Error fetching repositories for user: {} - {}", username, e.getMessage());
            throw new GithubClientException(e.getStatusCode(), e.getStatusText());
        }
    }

    public List<Branch> getBranches(String username, String repoName) {
        String url = UriComponentsBuilder.fromUriString(githubApiBaseUrl + "/repos/{username}/{repo}/branches")
                .buildAndExpand(username, repoName)
                .toUriString();
        log.info("Fetching branches for repository: {}/{}", username, repoName);
        try {
            Branch[] branches = restTemplate.getForObject(url, Branch[].class);
            return Optional.ofNullable(branches)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientResponseException e) {
            log.error("Error fetching branches for user: {} - {}", username, e.getMessage());
            throw new GithubClientException(e.getStatusCode(), e.getStatusText());
        }
    }

}
