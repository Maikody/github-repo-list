package com.example.github_repo_list.service;

import com.example.github_repo_list.client.GithubClient;
import com.example.github_repo_list.model.Branch;
import com.example.github_repo_list.model.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubClient githubClient;

    public List<Repository> getRepositoriesWithBranches(String username) {
        List<Repository> list = new ArrayList<>();
        for (Repository repo : githubClient.getRepositories(username)) {
            if (!repo.isFork()) {
                List<Branch> branches = githubClient.getBranches(username, repo.getName());
                repo.setBranches(branches);
                list.add(repo);
            }
        }
        return list;
    }
}

