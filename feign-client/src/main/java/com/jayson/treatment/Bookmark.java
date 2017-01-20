package com.jayson.treatment;


/**
 * Created by jayson on 11/13/16.
 */
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
