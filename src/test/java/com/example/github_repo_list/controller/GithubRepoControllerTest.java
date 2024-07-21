package com.example.github_repo_list.controller;

import com.example.github_repo_list.exception.GithubClientException;
import com.example.github_repo_list.model.Branch;
import com.example.github_repo_list.model.Repository;
import com.example.github_repo_list.service.GithubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GithubRepoController.class)
@ExtendWith(MockitoExtension.class)
class GithubRepoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubService githubService;

    @Test
    void shouldReturnOkWhenRepositoriesExistsForTheUser() throws Exception {
        Repository.Owner owner = new Repository.Owner("owner1");
        Branch.Commit commit = new Branch.Commit("sha1");
        Branch branch = new Branch("main", commit);
        Repository repository = new Repository("repo1", owner, false, Collections.singletonList(branch));

        when(githubService.getRepositoriesWithBranches(anyString())).thenReturn(Collections.singletonList(repository));

        mockMvc.perform(get("/repositories/username")
                        .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name':'repo1','owner':{'login':'owner1'},'fork':false,'branches':[{'name':'main','commit':{'sha':'sha1'}}]}]"));
    }

    @Test
    void shouldReturnNotFoundWhenUserNotFound() throws Exception {
        when(githubService.getRepositoriesWithBranches(anyString())).thenThrow(new GithubClientException(HttpStatus.NOT_FOUND, "Not found"));

        mockMvc.perform(get("/repositories/nonexistentuser")
                        .header("Accept", "application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{'status':404,'message':'Not found'}"));
    }

    @Test
    void shouldReturnBadRequestForInvalidAcceptHeader() throws Exception {
        mockMvc.perform(get("/repositories/username")
                        .header("Accept", "text/plain"))
                .andExpect(status().isNotAcceptable());
    }
}
