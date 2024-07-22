package com.example.github_repo_list.controller;

import com.example.github_repo_list.model.ErrorResponse;
import com.example.github_repo_list.model.Repository;
import com.example.github_repo_list.service.GithubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Fetch GitHub repositories for a user",
            description = "Retrieves a list of non-fork GitHub repositories for a specified user, including branch information and latest commit SHA.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of repositories",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Repository.class)))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":404,\"message\":\"Not found\"}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\":500,\"message\":\"Internal server error\"}")))
    })
    @GetMapping(value = "/repositories/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Repository>> getRepositories(@PathVariable String username) {
        List<Repository> repositories = githubService.getRepositoriesWithBranches(username);
        return ResponseEntity.ok(repositories);
    }

}
