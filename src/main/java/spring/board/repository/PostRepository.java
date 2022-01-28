package spring.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.thymeleaf.expression.Lists;
import spring.board.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public Post findOne(Long postId) {
        return em.find(Post.class, postId);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p")
                .getResultList();
    }

    public List<Post> findByTitle(String title) {
        return em.createQuery("select p from Post p where p.title = :title")
                .setParameter("title", title)
                .getResultList();
    }
}
