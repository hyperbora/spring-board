package io.github.hyperbora.spring.board.service;

import org.springframework.data.domain.Page;

import io.github.hyperbora.spring.board.domain.Board;

public interface BoardService {
    void insertBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(Board board);

    Board getBoard(Board board);

    Page<Board> getBoardList(Board board);
}
