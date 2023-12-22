package com.kec.plus.test;

import com.kec.plus.User.User;
import com.kec.plus.post.Post;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.SerializationUtils;

import java.time.LocalDateTime;

public class PostTestUtils {

    public static Post get(Post post, User user) {
        return get(post, 1L, LocalDateTime.now(), user);
    }

    public static Post get(Post post, Long id, LocalDateTime createDate, User user) {
        var newPost = SerializationUtils.clone(post);
        ReflectionTestUtils.setField(newPost, Post.class, "id", id, Long.class);
        ReflectionTestUtils.setField(newPost, Post.class, "createDate", createDate, LocalDateTime.class);
        newPost.setUser(user);
        return newPost;
    }
}
