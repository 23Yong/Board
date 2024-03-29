package spring.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import spring.board.common.util.FormDataEncoder;
import spring.board.config.TestSecurityConfig;
import spring.board.dto.ArticleCommentDto;
import spring.board.dto.request.ArticleCommentRequest;
import spring.board.service.ArticleCommentService;

import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 댓글")
@Import({TestSecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(ArticleCommentController.class)
class ArticleCommentControllerTest {

    private final MockMvc mvc;

    private final FormDataEncoder formDataEncoder;

    @MockBean
    private ArticleCommentService articleCommentService;

    public ArticleCommentControllerTest(
            @Autowired MockMvc mvc,
            @Autowired FormDataEncoder formDataEncoder) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }

    @WithUserDetails(value = "23YongTest", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[view] [POST] 댓글 등록 - 정상 호출")
    @Test
    void givenArticleCommentDto_whenRequesting_thenSavesNewArticleComment() throws Exception {
        // given
        long articleId = 1L;
        ArticleCommentRequest request = ArticleCommentRequest.of(articleId, "test comment");
        willDoNothing().given(articleCommentService).saveArticleComment(any(ArticleCommentDto.class));

        // when & then
        mvc.perform(post("/comments/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(formDataEncoder.encode(request))
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/" + articleId));

        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));
    }

    @WithUserDetails(value = "23YongTest", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[view] [POST] 댓글 삭제 - 정상 호출")
    @Test
    void givenArticleCommentIdToDelete_whenRequesting_thenDeletesArticleComment() throws Exception {
        // given
        long articleId = 1L;
        long articleCommentId = 1L;
        String userId = "23YongTest";
        willDoNothing().given(articleCommentService).deleteArticleComment(articleCommentId, userId);

        // when & then
        mvc.perform(
                post("/comments/" + articleCommentId + "/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formDataEncoder.encode(Map.of("articleId", articleId)))
                        .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/" + articleId));

        then(articleCommentService).should().deleteArticleComment(articleCommentId, userId);
    }

    @WithUserDetails(value = "23YongTest", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("[view] [POST] 대댓글 등록 - 정상 호출")
    @Test
    void givenArticleCommentInfoWithParentCommentId_whenRequesting_thenSaveNewChildComment() throws Exception {
        // given
        long articleId = 1L;
        ArticleCommentRequest request = ArticleCommentRequest.of(articleId, 1L, "test content");
        willDoNothing().given(articleCommentService).saveArticleComment(any(ArticleCommentDto.class));

        // when & then
        mvc.perform(
                post("/comments/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formDataEncoder.encode(request))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/" + articleId));

        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));
    }
}