package spring.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.board.domain.Member;
import spring.board.domain.Post;
import spring.board.repository.MemberRepository;
import spring.board.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    @Autowired PostRepository postRepository;
    @Autowired MemberRepository memberRepository;
    /**
     * 게시글 등록
     */
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
    public void updatePost(Long postId, String title, String content) {
        Post findPost = postRepository.findOne(postId);
        findPost.changePost(title, content);
    }
}
