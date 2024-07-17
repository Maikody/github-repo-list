package com.example.github_repo_list.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    private String name;
    private Commit commit;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Commit {
        private String sha;
    }
}
