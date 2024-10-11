package com.dogadopt.dog_adopt.controller;

import com.dogadopt.dog_adopt.dto.incoming.CommentCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.CommentInfo;
import com.dogadopt.dog_adopt.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{userId}/{dogId}")
    @ResponseStatus(CREATED)
    public CommentInfo makeNewComment(@PathVariable Long userId,
                                      @PathVariable Long dogId,
                                      @Valid @RequestBody CommentCreateUpdateCommand command) {
        log.info("Http request / POST / api / userId/ dogId, body: {}", command.toString());
        return commentService.makeNewComment(userId, dogId, command);
    }

    @DeleteMapping("/{userId}/{commentId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteComment(@PathVariable Long userId, @PathVariable Long commentId) {
        log.info("Http request / DELETE / api / userId/ commentId");
        commentService.deleteComment(userId, commentId);
    }

    @DeleteMapping("/admin/{commentId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCommentByAdmin(@PathVariable Long commentId) {
        log.info("Http request / DELETE / api / commentId");
        commentService.deleteCommentByAdmin(commentId);
    }
}
