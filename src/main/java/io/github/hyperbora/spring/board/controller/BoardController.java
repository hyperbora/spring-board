package io.github.hyperbora.spring.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.hyperbora.spring.board.domain.Board;
import io.github.hyperbora.spring.board.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @RequestMapping("/getBoardList")
    public String getBoardList(Model model, Board board) {
        Page<Board> boardList = boardService.getBoardList(board);
        model.addAttribute("boardList", boardList);
        return "board/getBoardList";
    }
}
