package io.github.hyperbora.spring.board.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.github.hyperbora.spring.board.domain.Board;
import io.github.hyperbora.spring.board.domain.Member;
import io.github.hyperbora.spring.board.domain.Role;
import io.github.hyperbora.spring.board.persistence.BoardRepository;
import io.github.hyperbora.spring.board.persistence.MemberRepository;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private BoardRepository boardRepo;

    @Value("${member.user.id}")
    private String userId;

    @Value("${member.admin.id}")
    private String adminId;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        if (memberRepo.count() == 0) {
            Member user = new Member();
            String userPassword = UUID.randomUUID().toString();
            user.setId(userId);
            user.setPassword(encoder.encode(userPassword));
            user.setName("홍길동");
            user.setRole(Role.ROLE_MEMBER);
            user.setEnabled(true);
            memberRepo.save(user);

            Member admin = new Member();
            String adminPassword = UUID.randomUUID().toString();
            admin.setId(adminId);
            admin.setPassword(encoder.encode(adminPassword));
            admin.setName("관리자");
            admin.setRole(Role.ROLE_ADMIN);
            admin.setEnabled(true);
            memberRepo.save(admin);

            for (int i = 1; i <= 13; i++) {
                Board board = new Board();
                board.setMember(user);
                board.setTitle(user.getName() + "(이)가 등록한 게시글 " + i);
                board.setContent(user.getName() + "(이)가 등록한 게시글 " + i + "입니다.");
                boardRepo.save(board);
            }

            for (int i = 1; i <= 13; i++) {
                Board board = new Board();
                board.setMember(admin);
                board.setTitle(admin.getName() + "(이)가 등록한 게시글 " + i);
                board.setContent(admin.getName() + "(이)가 등록한 게시글 " + i + "입니다.");
                boardRepo.save(board);
            }
            System.out.println("default user password : " + userPassword);
            System.out.println("default admin password : " + adminPassword);
        }
        return;
    }
}
