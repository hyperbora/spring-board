package io.github.hyperbora.spring.board.domain;

import lombok.Data;

@Data
public class Search {
    private String searchCondition;
    private String searchKeyword;
}
