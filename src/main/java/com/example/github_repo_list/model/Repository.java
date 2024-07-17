package com.example.github_repo_list.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository {
    private String name;
    private Owner owner;
    private boolean fork;
    private List<Branch> branches;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Owner {
        private String login;
    }
}
