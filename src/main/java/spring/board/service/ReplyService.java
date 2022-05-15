package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.controller.dto.ReplyDto;
import spring.board.domain.member.Member;
import spring.board.domain.post.Post;
import spring.board.domain.reply.Reply;
import spring.board.exception.member.UserNotFoundException;
import spring.board.exception.post.PostNotFoundException;
import spring.board.exception.reply.ReplyNotFoundException;
import spring.board.domain.member.MemberRepository;
import spring.board.domain.post.PostRepository;
import spring.board.domain.reply.ReplyRepository;

import java.util.List;

import static spring.board.controller.dto.ReplyDto.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long save(ReplySaveRequest requestDto, Long postId, Member member) {
        Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new PostNotFoundException("찾으려는 게시글이 없습니다."));


        Reply reply = requestDto.toEntity(post, member);
        replyRepository.save(reply);
        return reply.getId();
    }

    @Transactional
    public Long update(ReplyUpdateRequest requestDto) {
        Long id = requestDto.getId();
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new ReplyNotFoundException("찾으려는 댓글이 없습니다."));

        reply.editReply(requestDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(ReplyDeleteRequest requestDto) {
        Long id = requestDto.getId();
        replyRepository.deleteById(id);
        return id;
    }

    public Page<ReplyInfo> findAllReplies(Pageable pageable, Long postId) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 10);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("찾으려는 게시글이 없습니다."));
        Page<Reply> replies = new PageImpl<>(post.getReplies());

        return replies.map(reply -> ReplyInfo.builder()
                    .id(reply.getId())
                    .content(reply.getContent())
                    .nickname(reply.getWriter().getNickname())
                    .createdTime(reply.getCreatedTime())
                    .build()
        );
    }
}
