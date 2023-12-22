package com.kec.plus.post;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import com.kec.plus.User.UserRepository;
import com.kec.plus.test.PostTest;
import com.kec.plus.test.PostTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PostRepositoryTest implements PostTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(TEST_USER);
    }

    @Test
    @DisplayName("생성일시 기준 내림차순 정렬 조회")
    void findAll(){
        // given
        var testPost1 = PostTestUtils.get(TEST_POST, 1L, LocalDateTime.now().minusMinutes(2), TEST_USER);
        var testPost2 = PostTestUtils.get(TEST_POST, 2L, LocalDateTime.now().minusMinutes(1), TEST_USER);
        var testPost3 = PostTestUtils.get(TEST_POST, 3L, LocalDateTime.now(), TEST_USER);
        postRepository.save(testPost1);
        postRepository.save(testPost2);
        postRepository.save(testPost3);

        // when
        var resultPostList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));

        // then
        assertThat(resultPostList.get(0)).isEqualTo(testPost1);
        assertThat(resultPostList.get(1)).isEqualTo(testPost2);
        assertThat(resultPostList.get(2)).isEqualTo(testPost1);
    }
}
