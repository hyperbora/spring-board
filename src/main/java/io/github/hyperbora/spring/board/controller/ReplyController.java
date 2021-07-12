package io.github.hyperbora.spring.board.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public String saveReply(@RequestParam long seq, Reply reply, @AuthenticationPrincipal SecurityUser securityUser) {
        Optional<Board> board = boardRepository.findById(seq);
        if (securityUser != null && board.isPresent()) {
            Member member = securityUser.getMember();
            reply.setBoard(board.get());
            reply.setMember(member);
            replyRepository.save(reply);
        }
        return "redirect:/board/getBoard?seq=" + seq;
    }

    @PostMapping("/deleteReply")
    public String deleteReply(@RequestParam long seq, Reply reply, @AuthenticationPrincipal SecurityUser securityUser) {
        Optional<Reply> optionalReply = replyRepository.findById(reply.getId());
        if (securityUser != null && optionalReply.isPresent()) {
            Reply targetReply = optionalReply.get();
            if (targetReply.getMember().getId().equals(securityUser.getMember().getId())) {
                replyRepository.deleteById(targetReply.getId());
                log.info("reply is deleted Board id : {}, Reply Id : {}", seq, reply.getId());
            } else {
                log.warn("abnormal reply delete action Board id : {}, Reply Id : {}, User Id : {}", seq, reply.getId(),
                        securityUser.getMember().getId());
            }
        }
        return "redirect:/board/getBoard?seq=" + seq;
    }
}
