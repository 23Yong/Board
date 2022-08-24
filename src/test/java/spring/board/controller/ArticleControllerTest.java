package spring.board.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spring.board.common.config.SecurityConfig;
import spring.board.domain.article.SearchType;
import spring.board.dto.ArticleWithCommentsDto;
import spring.board.dto.UserAccountDto;
import spring.board.service.ArticleService;
import spring.board.service.PaginationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View Controller - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;

    @MockBean
    private ArticleService articleService;
    @MockBean
    private PaginationService paginationService;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[View] [GET] 게시글 리스트 (게시판) 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestingArticleView_thenReturnsArticlesView() throws Exception {
        // given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class)))
                .willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // when & then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));

        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @DisplayName("[View] [GET] 게시글 리스트 (게시판) 페이지 - 검색어와 함께 호출")
    @Test
    void givenKeyword_whenSearchingArticleView_thenReturnsArticlesView() throws Exception {
        // given
        SearchType searchType = SearchType.TITLE;
        String searchValue = "title";
        given(articleService.searchArticles(eq(searchType), eq(searchValue), any(Pageable.class)))
                .willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // when & then
        mvc.perform(get("/articles")
                        .queryParam("searchType", searchType.name())
                        .queryParam("searchValue", searchValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("searchTypes"));

        then(articleService).should().searchArticles(eq(searchType), eq(searchValue), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }


    @DisplayName("[View] [GET] 게시글 상세 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
        // given
        Long articeId = 1L;
        long totalCount = 1L;
        given(articleService.getArticle(articeId)).willReturn(createArticleWithCommentsDto());
        given(articleService.getArticleCount()).willReturn(totalCount);

        // when & then
        mvc.perform(get("/articles/" + articeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"))
                .andExpect(model().attribute("totalCount", totalCount));

        then(articleService).should().getArticle(articeId);
        then(articleService).should().getArticleCount();
    }

    @DisplayName("[View] [GET] 게시글 검색 전용 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        // given

        // when & then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search"));
    }

    @DisplayName("[View] [GET] 게시글 해시태그 검색 전용 페이지 - 정상호출")
    @Test
    void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
        // given

        // when & then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"));
    }

    @DisplayName("[view] [GET] 게시글 리스트 (게시판) 페이지 - 페이징, 정렬 기능")
    @Test
    void givenPagingAndSortingParams_whenSearchingArticlesPage_thenReturnsArticlesPage() throws Exception {
        // given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);
        given(articleService.searchArticles(null, null, pageable)).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // when
        mvc.perform(get("/articles")
                .queryParam("page", String.valueOf(pageNumber))
                .queryParam("size", String.valueOf(pageSize))
                .queryParam("sort", sortName + "," + direction)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("articles/index"))
        .andExpect(model().attributeExists("articles"))
        .andExpect(model().attribute("paginationBarNumbers", barNumbers));

        // then
        then(articleService).should().searchArticles(null, null, pageable);
        then(paginationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#Java",
                LocalDateTime.now(),
                "23Yong",
                LocalDateTime.now(),
                "23Yong"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                LocalDateTime.now(),
                "23Yong",
                LocalDateTime.now(),
                "23Yong",
                "23Yong",
                "23Yong@email.com",
                "password",
                "23Yong",
                "memo"
        );
    }
}