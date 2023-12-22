package com.kec.plus.comment;

import com.kec.plus.CommonResponseDTO;
import com.kec.plus.User.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponseDTO extends CommonResponseDTO {
    private Long id;
    private String text;
    private UserDTO user;
    private LocalDateTime createDate;

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = new UserDTO(comment.getUser());
        this.createDate = comment.getCreateDate();
    }
}