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
@RequestMapping("/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{userId}/{dogId}") //KEL A USER ID VAGY AZT KI TUDOM VENNI A LOGGED IN USERBŐL????
    @ResponseStatus(CREATED)
    public CommentInfo makeNewComment(@PathVariable Long userId,
                                      @PathVariable Long dogId,
                                      @Valid @RequestBody CommentCreateUpdateCommand command) {
        log.info("Http request / POST / dog-adopt / userId/ dogId, body: {}", command.toString());
        return commentService.makeNewComment(userId, dogId, command);
    }
}
