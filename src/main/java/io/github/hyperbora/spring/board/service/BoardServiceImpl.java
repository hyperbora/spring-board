package io.github.hyperbora.spring.board.service;

import com.querydsl.core.BooleanBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.github.hyperbora.spring.board.domain.Board;
import io.github.hyperbora.spring.board.domain.QBoard;
import io.github.hyperbora.spring.board.domain.Search;
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

    public Page<Board> getBoardList(Search search) {
        BooleanBuilder builder = new BooleanBuilder();

        QBoard qboard = QBoard.board;

        if (search.getSearchCondition().equals("TITLE")) {
            builder.and(qboard.title.like("%" + search.getSearchKeyword() + "%"));
        } else if (search.getSearchCondition().equals("CONTENT")) {
            builder.and(qboard.content.like("%" + search.getSearchKeyword() + "%"));
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "seq");
        return boardRepo.findAll(builder, pageable);
    }
}
