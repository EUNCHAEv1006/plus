package com.kec.plus.post;

import com.kec.plus.User.User;
import com.kec.plus.User.UserDTO;
import com.kec.plus.test.PostTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

import com.kec.plus.test.PostTestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest implements PostTest {
    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @DisplayName("게시글 생성")
    @Test
    void createPost() {
        // given
        var testPost = PostTestUtils.get(TEST_POST, TEST_USER);
        given(postRepository.save(any(Post.class))).willReturn(testPost);

        // when
        var result = postService.createPost(TEST_POST_REQUEST_DTO, TEST_USER);

        // then
        var expect = new PostResponseDTO(testPost);
        assertThat(result).isEqualTo(expect);
    }

    @DisplayName("게시글 DTO 조회")
    @Test
    void getPostDto() {
        // given
        var testPost = PostTestUtils.get(TEST_POST, TEST_USER);
        given(postRepository.findById(eq(TEST_POST_ID))).willReturn(Optional.of(testPost));

        // when
        var result = postService.getPostDto(TEST_POST_ID);

        // then
        assertThat(result).isEqualTo(new PostResponseDTO(testPost));
    }

    @DisplayName("게시글 리스트 맵 (최신순 정렬)")
    @Test
    void getUserPostMap() {
        // given
        var testPost1 = PostTestUtils.get(TEST_POST, 1L, LocalDateTime.now(), TEST_USER);
        var testPost2 = PostTestUtils.get(TEST_POST, 2L, LocalDateTime.now().minusMinutes(1), TEST_USER);
        var testAnotherPost = PostTestUtils.get(TEST_POST, 1L, LocalDateTime.now(), TEST_ANOTHER_USER);

        given(postRepository.findAll(any(Sort.class))).willReturn(List.of(testPost1, testPost2, testAnotherPost));

        // when
        var result = postService.getUserPostMap();

        // then
        assertThat(result.get(new UserDTO(TEST_USER)).get(0)).isEqualTo(new PostResponseDTO(testPost1));
        assertThat(result.get(new UserDTO(TEST_USER)).get(1)).isEqualTo(new PostResponseDTO(testPost2));
        assertThat(result.get(new UserDTO(TEST_ANOTHER_USER)).get(0)).isEqualTo(new PostResponseDTO(testAnotherPost));
    }

    @DisplayName("게시글 수정")
    @Test
    void updatePost() {
        // given
        ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
        var testPost = PostTestUtils.get(TEST_POST, TEST_USER);
        given(postRepository.findById(eq(TEST_POST_ID))).willReturn(Optional.of(testPost));

        // when
        var request = new PostRequestDTO("updateTitle", "updateContent");
        var result = postService.updatePost(TEST_POST_ID, request, TEST_USER);

        // then
        assertThat(result).isEqualTo(new PostResponseDTO(testPost));
    }

    @DisplayName("게시글 조회")
    @Test
    void getPost() {
        // given
        var testPost = PostTestUtils.get(TEST_POST, TEST_USER);
        given(postRepository.findById(eq(TEST_POST_ID))).willReturn(Optional.of(testPost));

        // when
        var result = postService.getPost(TEST_POST_ID);

        // then
        assertThat(result).isEqualTo(testPost);
    }

    @DisplayName("유저의 게시글 조회")
    @Nested
    class getUserPost {
        @DisplayName("유저의 게시글 조회 성공")
        @Test
        void getUserPost_success() {
            // given
            ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
            var testPost = PostTestUtils.get(TEST_POST, TEST_USER);
            given(postRepository.findById(eq(TEST_POST_ID))).willReturn(Optional.of(testPost));

            // when
            var result = postService.getUserPost(TEST_POST_ID, TEST_USER);

            // then
            assertThat(result).isEqualTo(testPost);
        }

        @DisplayName("유저의 게시글 조회 실패 - 조회 권한 없음")
        @Test
        void getUserPost_fail() {
            // given
            ReflectionTestUtils.setField(TEST_USER, User.class, "id", TEST_USER_ID, Long.class);
            var testPost = PostTestUtils.get(TEST_POST, TEST_USER);
            given(postRepository.findById(eq(TEST_POST_ID))).willReturn(Optional.of(testPost));
            ReflectionTestUtils.setField(TEST_ANOTHER_USER, User.class, "id", TEST_ANOTHER_USER_ID, Long.class);

            // when & exception
            assertThrows(RejectedExecutionException.class, () -> postService.getUserPost(TEST_POST_ID, TEST_ANOTHER_USER));
        }
    }
}
