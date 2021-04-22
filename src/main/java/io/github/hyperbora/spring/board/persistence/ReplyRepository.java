package io.github.hyperbora.spring.board.persistence;

import org.springframework.data.repository.CrudRepository;

import io.github.hyperbora.spring.board.domain.Reply;

public interface ReplyRepository extends CrudRepository<Reply, Long> {

}
