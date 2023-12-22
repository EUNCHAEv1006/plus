package com.kec.plus.post;

import com.kec.plus.CommonResponseDTO;
import com.kec.plus.User.UserDTO;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class PostResponseDTO extends CommonResponseDTO {
    private Long id;
    private String title;
    private String content;
    private Boolean isCompleted;
    private UserDTO user;
    private LocalDateTime createDate;

    public PostResponseDTO(String msg, Integer statusCode) {
        super(msg, statusCode);
    }

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.isCompleted = post.getIsCompleted();
        this.user = new UserDTO(post.getUser());
        this.createDate = post.getCreateDate();
    }
}
