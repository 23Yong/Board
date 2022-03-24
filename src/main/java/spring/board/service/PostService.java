package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.controller.dto.PostDto;
import spring.board.domain.Member;
import spring.board.domain.Post;
import spring.board.exception.post.PostNotFoundException;
import spring.board.repository.MemberRepository;
import spring.board.repository.PostRepository;

import static spring.board.controller.dto.PostDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 등록 (회원가입 필요)
     */
    @Transactional
    public void registerPost(String memberLoginId, Post post) {

        Member member = memberRepository.findByLoginId(memberLoginId)
                .orElseThrow();
        post.setMember(member);

        postRepository.save(post);
    }

    /**
     * 게시글 하나 조회
     */
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("해당 아이디를 가진 게시글이 존재하지 않습니다."));
    }

    /**
     * 게시글 전체 조회
     */
    public Page<Post> findAllPosts(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 10);
        return postRepository.findAll(pageable);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(PostEditRequest request) {
        Post findPost = postRepository.findById(request.getPostId())
                .orElseThrow();

        findPost.changePost(request.getTitle(), request.getContent());
    }
}
