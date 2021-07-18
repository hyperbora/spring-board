package io.github.hyperbora.spring.board;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.querydsl.core.BooleanBuilder;

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
import io.github.hyperbora.spring.board.domain.QBoard;
import io.github.hyperbora.spring.board.domain.Reply;
import io.github.hyperbora.spring.board.domain.Role;
import io.github.hyperbora.spring.board.persistence.BoardRepository;
import io.github.hyperbora.spring.board.persistence.MemberRepository;
import io.github.hyperbora.spring.board.persistence.ReplyRepository;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
public class ReplyRepositoryTest {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private ReplyRepository replyRepo;

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
        Board board = new Board();
        board.setTitle("Test Title");
        board.setContent("Test Content");
        board.setMember(member);
        boardRepo.save(board);
    }

    @Test
    public void testInsert() {
        Optional<Member> user = memberRepo.findById("user");
        if (user.isPresent()) {
            BooleanBuilder builder = new BooleanBuilder();

            QBoard qboard = QBoard.board;

            builder.and(qboard.title.eq("Test Title"));
            Optional<Board> optionalBoard = boardRepo.findOne(builder);
            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                Reply reply = new Reply();
                reply.setMember(user.get());
                reply.setBoard(board);
                reply.setContent("Test Reply");
                replyRepo.save(reply);
                assertTrue(reply.getId() > 0, "Reply Insert Failed");
            }
        }
    }
}
