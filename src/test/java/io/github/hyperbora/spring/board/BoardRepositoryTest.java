package io.github.hyperbora.spring.board;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.hyperbora.spring.board.domain.Board;
import io.github.hyperbora.spring.board.domain.Member;
import io.github.hyperbora.spring.board.domain.Role;
import io.github.hyperbora.spring.board.persistence.BoardRepository;
import io.github.hyperbora.spring.board.persistence.MemberRepository;

@DataJpaTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class BoardRepositoryTest {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private BoardRepository boardRepo;

    @BeforeAll
    public void before() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        Member member = new Member();
        member.setId("user");
        member.setName("Tom");
        member.setPassword(encoder.encode("1q2w3e4r"));
        member.setEnabled(true);
        member.setRole(Role.ROLE_MEMBER);
        memberRepo.save(member);
    }

    @Test
    public void testInsert() {
        Optional<Member> user = memberRepo.findById("user");
        if (user.isPresent()) {
            Board board = new Board();
            board.setTitle("Hello");
            board.setContent("World");
            board.setMember(user.get());
            boardRepo.save(board);
            assertTrue(boardRepo.count() > 0);
        }
    }

}
