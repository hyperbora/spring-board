package io.github.hyperbora.spring.board.persistence;

import org.springframework.data.repository.CrudRepository;

import io.github.hyperbora.spring.board.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

}
