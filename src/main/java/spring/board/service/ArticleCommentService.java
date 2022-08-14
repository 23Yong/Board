package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.article.Article;
import spring.board.domain.member.Member;
import spring.board.domain.articlecomment.ArticleComment;
import spring.board.exception.post.PostNotFoundException;
import spring.board.exception.reply.ReplyNotFoundException;
import spring.board.domain.articlecomment.ArticleCommentRepository;

import static spring.board.controller.dto.ReplyDto.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;

    private final spring.board.domain.article.ArticleRepository articleRepository;

    @Transactional
    public Long save(ReplySaveRequest requestDto, Long postId, Member member) {
        Article article = articleRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("찾으려는 게시글이 없습니다."));

        ArticleComment articleComment = requestDto.toEntity(article, member);
        articleCommentRepository.save(articleComment);
        return articleComment.getId();
    }

    @Transactional
    public Long update(ReplyUpdateRequest requestDto) {
        Long id = requestDto.getId();
        ArticleComment articleComment = articleCommentRepository.findById(id)
                .orElseThrow(() -> new ReplyNotFoundException("찾으려는 댓글이 없습니다."));

        articleComment.editReply(requestDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(ReplyDeleteRequest requestDto) {
        Long id = requestDto.getId();
        articleCommentRepository.deleteById(id);
        return id;
    }

    public Page<ReplyInfo> findAllReplies(Pageable pageable, Long postId) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 10);

        Article article = articleRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("찾으려는 게시글이 없습니다."));
        Page<ArticleComment> replies = new PageImpl<>(article.getArticleComments());

        return replies.map(reply -> ReplyInfo.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .nickname(reply.getWriter().getNickname())
                .createdTime(reply.getCreatedAt())
                .build()
        );
    }
}