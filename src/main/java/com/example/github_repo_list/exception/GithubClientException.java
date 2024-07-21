package com.example.github_repo_list.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class GithubClientException extends HttpClientErrorException {

    public GithubClientException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }

}
