package com.kec.plus.post;

import com.kec.plus.User.UserDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class PostListResponseDTO {
    private UserDTO user;
    private List<PostResponseDTO> postList;

    public PostListResponseDTO(UserDTO user, List<PostResponseDTO> postList) {
        this.user = user;
        this.postList = postList;
    }
}
