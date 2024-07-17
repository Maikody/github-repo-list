package com.example.github_repo_list.service;

import com.example.github_repo_list.model.Branch;
import com.example.github_repo_list.model.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubService githubService;

    @Test
    void shouldSuccessfullyGetRepositories() {
        Repository[] repositories = {
                new Repository("repo1", new Repository.Owner("owner1"), false, null),
                new Repository("repo2", new Repository.Owner("owner2"), false, null)
        };

        when(restTemplate.getForObject(anyString(), eq(Repository[].class))).thenReturn(repositories);

        List<Repository> result = githubService.getRepositories("username");

        assertEquals(2, result.size());
        assertEquals("repo1", result.getFirst().getName());
        assertEquals("owner1", result.getFirst().getOwner().getLogin());
        assertEquals("repo2", result.get(1).getName());
        assertEquals("owner2", result.get(1).getOwner().getLogin());
    }

    @Test
    void shouldReturnEmptyListForNonExistingRepositories() {
        when(restTemplate.getForObject(anyString(), eq(Repository[].class))).thenReturn(null);

        List<Repository> result = githubService.getRepositories("username");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSuccessfullyReturnGetBranches() {
        Branch[] branches = {
                new Branch("main", new Branch.Commit("sha1")),
                new Branch("feature", new Branch.Commit("sha2"))
        };

        when(restTemplate.getForObject(anyString(), eq(Branch[].class))).thenReturn(branches);

        List<Branch> result = githubService.getBranches("username", "repo");

        assertEquals(2, result.size());
        assertEquals("main", result.getFirst().getName());
        assertEquals("sha1", result.getFirst().getCommit().getSha());
        assertEquals("feature", result.get(1).getName());
        assertEquals("sha2", result.get(1).getCommit().getSha());
    }

    @Test
    void shouldReturnEmptyListForNonExistingBranches() {
        when(restTemplate.getForObject(anyString(), eq(Branch[].class))).thenReturn(null);

        List<Branch> result = githubService.getBranches("username", "repo");

        assertTrue(result.isEmpty());
    }
}
