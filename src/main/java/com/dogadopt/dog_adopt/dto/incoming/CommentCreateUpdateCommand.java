package com.dogadopt.dog_adopt.dto.incoming;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateUpdateCommand {

    @Size(max = 200, message = "Comment can ba maximum {max} characters.")
    private String commentText;
}
