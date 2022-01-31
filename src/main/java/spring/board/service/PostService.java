package spring.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.board.domain.Member;
import spring.board.domain.Post;
import spring.board.repository.MemberRepository;
import spring.board.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    /**
     * 게시글 등록
     */
    @Transactional
    public Long registerPost(Long memberId, Post post) {

        Member findMember = memberRepository.findOne(memberId);

        post.setMember(findMember);

        return postRepository.save(post);
    }

    /**
     * 게시글 하나 조회
     */
    public Post findPost(Long postId) {
        return postRepository.findOne(postId);
    }

    /**
     * 게시글 전체 조회
     */
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(Long postId, String title, String content) {
        Post findPost = postRepository.findOne(postId);
        findPost.changePost(title, content);
    }
}
