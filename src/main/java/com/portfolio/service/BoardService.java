package com.portfolio.service;

import com.portfolio.controller.board.BoardForm;
import com.portfolio.domain.Board;
import com.portfolio.domain.Comment;
import com.portfolio.domain.Member;
import com.portfolio.repository.BoardRepository;
import com.portfolio.repository.CommentRepository;
import com.portfolio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public Board createNewBoard(BoardForm boardForm) {
        Board board = Board.builder()
                .title(boardForm.getTitle())
                .description(boardForm.getDescription())
                .build();
        board.settingTime();

        return boardRepository.save(board);
    }

    public void addMember(Member member, Optional<Board> byId) {
        byId.ifPresent(a->a.getMembers().add(member));
    }

    public void addReply(String reply, Member member) {
        Comment comment = Comment.builder()
                .reply(reply)
                .time(LocalDate.now())
                .build();

        commentRepository.save(comment);

        Optional<Member> byId = memberRepository.findById(member.getId());
        byId.ifPresent(a->a.getComments().add(comment));
    }
}
