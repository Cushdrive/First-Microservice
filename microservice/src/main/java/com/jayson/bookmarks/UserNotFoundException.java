package com.jayson.bookmarks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jayson on 11/5/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException  {
    public UserNotFoundException(String userId) {
        super("Could not find user '" + userId + "'.");
    }
}