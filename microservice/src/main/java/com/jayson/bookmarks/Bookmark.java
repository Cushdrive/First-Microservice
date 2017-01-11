package com.jayson.bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by jayson on 11/5/16.
 */
@Entity
public class Bookmark {

    public String description;
    public String uri;

    @JsonIgnore
    @ManyToOne
    private Account account;

    @Id
    @GeneratedValue
    private Long id;

    Bookmark() {
        // jpa only
    }

    public Bookmark (Account account, String uri, String description) {
        this.uri = uri;
        this.description = description;
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {

        return id;
    }
}
