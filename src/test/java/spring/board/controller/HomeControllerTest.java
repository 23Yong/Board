package spring.board.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import spring.board.controller.dto.PostDto;
import spring.board.domain.Member;
import spring.board.domain.Post;
import spring.board.service.MemberService;
import spring.board.service.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PostService postService;
    @MockBean
    MemberService memberService;


    @Test
    public void 게시글_전체_조회() throws Exception {
        // given
        Member member = Member.builder()
                .loginId("loginId1")
                .password("password1")
                .nickname("nickname1")
                .build();

        List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .title("title1")
                .content("content")
                .createdTime(LocalDateTime.now())
                .build();

        posts.add(post);

        // when
        memberService.join(member);
        postService.registerPost(member.getId(), post);

        // then
        BDDMockito.given(postService.findAllPosts()).willReturn(posts);
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title1")));
    }
}