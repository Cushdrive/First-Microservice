package com.jayson.microservice_client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jayson on 11/6/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bookmark {
    private String uri;
    private String description;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "The bookmark is: " + description + " at: " + uri;
    }
}
