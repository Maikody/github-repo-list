package com.example.github_repo_list.service;

import com.example.github_repo_list.client.GithubClient;
import com.example.github_repo_list.model.Branch;
import com.example.github_repo_list.model.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @Mock
    private GithubClient githubClient;

    @InjectMocks
    private GithubService githubService;

    @Test
    void shouldSuccessfullyReturnRepositoriesWithBranches() {
        List<Repository> repositories = getRepositories();
        List<Branch> branches = getBranches();

        when(githubClient.getRepositories(anyString())).thenReturn(repositories);
        when(githubClient.getBranches(anyString(), anyString())).thenReturn(branches);

        List<Repository> result = githubService.getRepositoriesWithBranches("username");

        assertEquals(2, result.size());
        assertEquals("repo1", result.getFirst().getName());
        assertEquals("main", result.getFirst().getBranches().getFirst().getName());
        assertEquals("feature", result.getFirst().getBranches().get(1).getName());
        assertEquals("sha1", result.getFirst().getBranches().getFirst().getCommit().getSha());
        assertEquals("sha2", result.getFirst().getBranches().get(1).getCommit().getSha());
        assertEquals("repo2", result.get(1).getName());
        assertEquals("main", result.get(1).getBranches().getFirst().getName());
        assertEquals("feature", result.get(1).getBranches().get(1).getName());
        assertEquals("sha1", result.get(1).getBranches().getFirst().getCommit().getSha());
        assertEquals("sha2", result.get(1).getBranches().get(1).getCommit().getSha());
    }

    private static List<Repository> getRepositories() {

        return List.of(
                new Repository("repo1", new Repository.Owner("owner1"), false, null),
                new Repository("repo2", new Repository.Owner("owner2"), false, null)
        );
    }

    private static List<Branch> getBranches() {
        return List.of(
                new Branch("main", new Branch.Commit("sha1")),
                new Branch("feature", new Branch.Commit("sha2"))
        );
    }
}
