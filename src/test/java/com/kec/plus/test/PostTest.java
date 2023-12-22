package com.kec.plus.test;

import com.kec.plus.post.Post;
import com.kec.plus.post.PostRequestDTO;
import com.kec.plus.post.PostResponseDTO;

public interface PostTest extends CommonTest{

    Long TEST_POST_ID = 1L;
    String TEST_POST_TITLE = "title";
    String TEST_POST_CONTENT = "content";

    PostRequestDTO TEST_POST_REQUEST_DTO = PostRequestDTO.builder()
            .title(TEST_POST_TITLE)
            .content(TEST_POST_CONTENT)
            .build();
    PostResponseDTO TEST_POST_RESPONSE_DTO = PostResponseDTO.builder()
            .title(TEST_POST_TITLE)
            .content(TEST_POST_CONTENT)
            .build();
    Post TEST_POST = Post.builder()
            .title(TEST_POST_TITLE)
            .content(TEST_POST_CONTENT)
            .build();
    Post TEST_ANOTHER_POST = Post.builder()
            .title(ANOTHER_PREFIX + TEST_POST_TITLE)
            .content(ANOTHER_PREFIX + TEST_POST_CONTENT)
            .build();
}
