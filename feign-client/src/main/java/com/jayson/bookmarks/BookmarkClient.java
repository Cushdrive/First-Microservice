package com.jayson.bookmarks;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jayson on 11/13/16.
 */
@FeignClient("bookmark-service")
public interface BookmarkClient {
    @RequestMapping(method = RequestMethod.GET, value = "/{username}/bookmarks/1")
    Bookmark getMyLatestBookmark(@PathVariable("username") String username) throws Exception;
}
