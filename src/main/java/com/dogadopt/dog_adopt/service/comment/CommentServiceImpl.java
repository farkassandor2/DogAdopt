package com.dogadopt.dog_adopt.service.comment;

import com.dogadopt.dog_adopt.config.security.AuthUserService;
import com.dogadopt.dog_adopt.domain.AppUser;
import com.dogadopt.dog_adopt.domain.Comment;
import com.dogadopt.dog_adopt.domain.Dog;
import com.dogadopt.dog_adopt.dto.incoming.CommentCreateUpdateCommand;
import com.dogadopt.dog_adopt.dto.outgoing.CommentInfo;
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
    private final AuthUserService authUserService;
    private final DogService dogService;
    private final ModelMapper modelMapper;


    @Override
    public CommentInfo makeNewComment(Long userId, Long dogId, CommentCreateUpdateCommand command) {

        AppUser user = appUserService.findActiveUserById(userId);
//        AppUser currentUser = (AppUser) authUserService.getUserFromSession();
        Dog dog = dogService.getOneDog(dogId);
        CommentInfo commentInfo = null;

        if (user != null /*&& user == currentUser*/ && dog != null) {
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
