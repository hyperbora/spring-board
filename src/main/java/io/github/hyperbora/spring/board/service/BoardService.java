package io.github.hyperbora.spring.board.service;

import org.springframework.data.domain.Page;

import io.github.hyperbora.spring.board.domain.Board;
import io.github.hyperbora.spring.board.domain.Search;

public interface BoardService {
    void insertBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(Board board);

    Board getBoard(Board board);

    Page<Board> getBoardList(Search search);
}
