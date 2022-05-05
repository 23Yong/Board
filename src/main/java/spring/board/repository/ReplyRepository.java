package spring.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.board.controller.dto.ReplyDto;
import spring.board.domain.Reply;

import static spring.board.controller.dto.ReplyDto.*;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
