package com.dogadopt.dog_adopt.service.comment;

import com.dogadopt.dog_adopt.dto.incoming.CommentCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.CommentInfo;
import jakarta.validation.Valid;

public interface CommentService {

    CommentInfo makeNewComment(Long userId, Long dogId, @Valid CommentCreateUpdateCommand command);

    void deleteComment(Long userId, Long commentId);

    void deleteCommentByAdmin(Long commentId);
}
