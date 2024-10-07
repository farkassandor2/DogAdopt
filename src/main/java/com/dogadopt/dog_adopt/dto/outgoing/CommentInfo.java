package com.dogadopt.dog_adopt.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfo {

    private Long idComment;

    private Long idUser;

    private Long idDog;

    private String commentText;

    private LocalDateTime createdAt;

}
