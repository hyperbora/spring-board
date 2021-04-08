package io.github.hyperbora.spring.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.github.hyperbora.spring.board.domain.Board;
import io.github.hyperbora.spring.board.persistence.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepo;

    public void insertBoard(Board board) {
        boardRepo.save(board);
    }

    public void updateBoard(Board board) {
        Board findBoard = boardRepo.findById(board.getSeq()).get();

        findBoard.setTitle(board.getTitle());
        findBoard.setContent(board.getContent());
        boardRepo.save(findBoard);
    }

    public void deleteBoard(Board board) {
        boardRepo.delete(board);
    }

    public Board getBoard(Board board) {
        return boardRepo.findById(board.getSeq()).get();
    }

    public Page<Board> getBoardList(Board board) {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "seq");
        return boardRepo.getBoardList(pageable);
    }
}
