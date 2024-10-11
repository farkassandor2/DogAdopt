package com.dogadopt.dog_adopt.service.comment;

import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Comment;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.dto.incoming.CommentCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.CommentInfo;
import com.dogadopt.dog_adopt.exception.CommentDoesNotBelongToUserException;
import com.dogadopt.dog_adopt.exception.CommentNotFoundException;
import com.dogadopt.dog_adopt.repository.CommentRepository;
import com.dogadopt.dog_adopt.service.dog.DogService;
import com.dogadopt.dog_adopt.service.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final AppUserService appUserService;
    private final DogService dogService;
    private final ModelMapper modelMapper;


    @Override
    public CommentInfo makeNewComment(Long userId, Long dogId, CommentCreateUpdateCommand command) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser currentUser = appUserService.getLoggedInCustomer();
        Dog dog = dogService.getOneDog(dogId);
        CommentInfo commentInfo = null;

        if (user != null && user == currentUser && dog != null) {
            Comment comment = modelMapper.map(command, Comment.class);
            comment.setDog(dog);
            comment.setUser(user);
            commentRepository.save(comment);
            user.setComments(new ArrayList<>(List.of(comment)));
            dog.setComments(new ArrayList<>(List.of(comment)));
            commentInfo = generateCommentInfo(user, dog, comment);
        }

        return commentInfo;
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {

        AppUser user = appUserService.findActiveUserById(userId);
        AppUser currentUser = appUserService.getLoggedInCustomer();

        if (user != null && user == currentUser) {
            Comment comment = getCommentById(commentId);
            boolean isCommentBelongToUser = checkIfCommentBelongsToUser(user, comment);

            if (isCommentBelongToUser) {
                commentRepository.deleteById(commentId);
            } else {
                throw new CommentDoesNotBelongToUserException(
                        "Comment with id " + commentId + " does not belong to id " + userId);
            }
        }
    }

    private boolean checkIfCommentBelongsToUser(AppUser user, Comment comment) {

//        Comment comment1 = commentRepository
        return true;
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment don't exist with id " + commentId));
    }

    @Override
    public void deleteCommentByAdmin(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private CommentInfo generateCommentInfo(AppUser user, Dog dog, Comment comment) {
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setIdComment(comment.getId());
        commentInfo.setIdUser(user.getId());
        commentInfo.setUserFirstName(user.getFirstName());

        String userLastName = user.getLastName();
        if (userLastName == null) {
            commentInfo.setUserLastName(user.getEmail());
        } else {
            commentInfo.setUserLastName(user.getLastName());
        }

        commentInfo.setIdDog(dog.getId());
        commentInfo.setDogName(dog.getName());
        commentInfo.setCommentText(comment.getCommentText());
        commentInfo.setCreatedAt(comment.getCreatedAt());
        return commentInfo;
    }
}
