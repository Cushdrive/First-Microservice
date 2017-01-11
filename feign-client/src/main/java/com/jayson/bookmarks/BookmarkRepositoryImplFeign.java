package com.jayson.bookmarks;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jayson on 11/13/16.
 */
@RestController
public class BookmarkRepositoryImplFeign {
    private final BookmarkClient bookmarkClient;

    @Autowired
    BookmarkRepositoryImplFeign(BookmarkClient bookmarkClient) {
        this.bookmarkClient = bookmarkClient;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myfirstbookmark")
    public String getMyLatestBookmark(@RequestParam("username") String username) throws Exception {

        return this.bookmarkClient.getMyLatestBookmark(username).toString();
    }
}
