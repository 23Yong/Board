package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.article.Article;
import spring.board.domain.member.Member;
import spring.board.exception.member.UserNotFoundException;
import spring.board.exception.post.PostNotFoundException;
import spring.board.domain.member.MemberRepository;
import spring.board.domain.article.ArticleRepository;

import static spring.board.controller.dto.PostDto.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(PostSaveRequest requestDto, String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserNotFoundException("찾으려는 회원이 없습니다."));

        Article article = articleRepository.save(requestDto.toEntity(member));
        article.setMember(member);

        return article.getId();
    }

    @Transactional
    public Long update(PostUpdateRequest request) {
        Article article = articleRepository.findById(request.getId())
                .orElseThrow(() -> new PostNotFoundException("찾으려는 게시물이 없습니다."));

        article.changeArticle(request.getTitle(), request.getContent());
        return article.getId();
    }

    @Transactional
    public Long delete(Long id) {
        articleRepository.deleteById(id);
        return id;
    }

    public Article findPost(Long postId) {
        return articleRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 아이디를 가진 게시글이 존재하지 않습니다."));
    }

    public Page<PostInfo> findAllPosts(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 10);

        return articleRepository.findAll(pageable)
                .map(PostInfo::new);
    }
}
