package spring.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.board.domain.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);
}
