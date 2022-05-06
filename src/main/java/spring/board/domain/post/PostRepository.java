package spring.board.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.board.domain.post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
