package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.member.Member;
import spring.board.domain.post.Post;
import spring.board.domain.reply.Reply;
import spring.board.exception.member.UserNotFoundException;
import spring.board.exception.post.PostNotFoundException;
import spring.board.exception.reply.ReplyNotFoundException;
import spring.board.domain.member.MemberRepository;
import spring.board.domain.post.PostRepository;
import spring.board.domain.reply.ReplyRepository;

import static spring.board.controller.dto.ReplyDto.*;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long registerReply(ReplySaveRequest replyDto) {
        Member member = memberRepository.findByNickname(replyDto.getNickname())
                .orElseThrow(() -> new UserNotFoundException("찾으려는 회원이 없습니다."));

        Post post = postRepository.findById(replyDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("찾으려는 게시글이 없습니다."));

        Reply reply = replyDto.toEntity(replyDto.getContent());

        reply.addWriter(member);
        reply.addPost(post);

        return replyRepository.save(reply).getId();
    }

    @Transactional(readOnly = true)
    public Page<ReplyResponse> findAllReplies(Pageable pageable) {
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber()-1;
        pageable = PageRequest.of(page, 10);

        return replyRepository.findAll(pageable)
                .map(ReplyResponse::new);
    }

    @Transactional
    public void editReply(ReplyRequest replyDto) {
        Reply reply = replyRepository.findById(replyDto.getId())
                .orElseThrow(() -> new ReplyNotFoundException("찾으려는 댓글이 없습니다."));

        reply.editReply(replyDto.getContent());
    }

    @Transactional
    public void deleteReply(ReplyRequest replyDto) {
        Reply reply = replyRepository.findById(replyDto.getId())
                .orElseThrow(() -> new ReplyNotFoundException("찾으려는 댓글이 없습니다."));

        replyRepository.delete(reply);
    }
}
