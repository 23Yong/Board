package spring.board.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.board.domain.reply.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
