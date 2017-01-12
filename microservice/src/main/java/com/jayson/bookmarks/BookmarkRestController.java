package com.jayson.bookmarks;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Created by jayson on 11/5/16.
 */
@RestController
@RequestMapping("/api/{userId}/bookmarks")
public class BookmarkRestController {
    private static final Logger log = LoggerFactory.getLogger(BookmarkRestController.class);
    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    BookmarkRestController(BookmarkRepository bookmarkRepository,
                           AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Bookmark> readBookmarks(@PathVariable String userId) {
        try {
            this.validateUser(userId);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return this.bookmarkRepository.findByAccountUsername(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
        try {
            this.validateUser(userId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        return this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                    Bookmark result = bookmarkRepository.save(new Bookmark(account, input.uri, input.description));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId, HttpServletRequest request) {
        try {
            this.validateUser(userId);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        log.info("Servicing request for: " + request.getRequestURI());
        Bookmark temp = this.bookmarkRepository.findOne(bookmarkId);
        if (userId.compareTo(temp.getAccount().getUsername().toString()) == 0) {
            return temp;
        }
        else
        {
            throw new ItemNotFoundException(bookmarkId.toString());
        }
    }

    private void validateUser(String userId) throws UserNotFoundException {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId.toString()));
    }
}
