package io.github.hyperbora.spring.board;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.hyperbora.spring.board.domain.Board;
import io.github.hyperbora.spring.board.domain.Member;
import io.github.hyperbora.spring.board.domain.Role;
import io.github.hyperbora.spring.board.persistence.BoardRepository;
import io.github.hyperbora.spring.board.persistence.MemberRepository;

@DataJpaTest
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

    @Test
    public void testUpdate() {
        Optional<Member> user = memberRepo.findById("user");
        long id = 0;
        if (user.isPresent()) {
            Board board = new Board();
            board.setTitle("Hello");
            board.setContent("World");
            board.setMember(user.get());
            boardRepo.save(board);
            id = board.getSeq();
        }
        if (id > 0L) {
            Optional<Board> optionalBoard = boardRepo.findById(id);
            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                board.setTitle("New Hello");
                board.setContent("New World");
                boardRepo.save(board);
            }
            optionalBoard = boardRepo.findById(id);
            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                assertTrue(board.getTitle().equals("New Hello"), "Board Title Update Failed");
                assertTrue(board.getContent().equals("New World"), "Board Content Update Failed");
            }
        }
    }

    @Test
    public void testDelete() {
        Optional<Member> user = memberRepo.findById("user");
        long id = 0;
        if (user.isPresent()) {
            Board board = new Board();
            board.setTitle("Hello");
            board.setContent("World");
            board.setMember(user.get());
            boardRepo.save(board);
            id = board.getSeq();
        }
        if (id > 0L) {
            boardRepo.deleteById(id);
            Optional<Board> optionalBoard = boardRepo.findById(id);
            assertTrue(optionalBoard.isEmpty(), "Board Delete Failed");
        }
    }
}
