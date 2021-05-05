package io.github.hyperbora.spring.board.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.hyperbora.spring.board.domain.Board;
import io.github.hyperbora.spring.board.domain.Member;
import io.github.hyperbora.spring.board.domain.Reply;
import io.github.hyperbora.spring.board.persistence.BoardRepository;
import io.github.hyperbora.spring.board.persistence.ReplyRepository;
import io.github.hyperbora.spring.board.security.SecurityUser;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @PostMapping
    public String saveReply(@RequestParam long seq, Reply reply, @AuthenticationPrincipal SecurityUser securityUser) {
        Optional<Board> board = boardRepository.findById(seq);
        if (securityUser != null) {
            if (board.isPresent()) {
                Board _board = board.get();
                Member member = securityUser.getMember();
                reply.setBoard(_board);
                reply.setMember(member);
                replyRepository.save(reply);
            }
        }
        return "redirect:/board/getBoard?seq=" + seq;
    }
}
