package io.github.hyperbora.spring.board.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = { "member", "board" })
@Entity
public class Reply {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createDate = new Date();

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false)
    private Member member;

    public void setBoard(Board board) {
        this.board = board;
        board.getReplyList().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
