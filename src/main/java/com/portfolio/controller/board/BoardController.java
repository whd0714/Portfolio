package com.portfolio.controller.board;

import com.portfolio.controller.member.CurrentUser;
import com.portfolio.domain.Board;
import com.portfolio.domain.Comment;
import com.portfolio.domain.Keyword;
import com.portfolio.domain.Member;
import com.portfolio.repository.BoardRepository;
import com.portfolio.repository.CommentRepository;
import com.portfolio.repository.MemberRepository;
import com.portfolio.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final MemberRepository memberRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

/*    @GetMapping("/board/{path}")
    public String viewBoard(@CurrentUser Member member, Model model, @PathVariable Long path){
        model.addAttribute(member);

        List<Board> all = boardRepository.findAll();
        Optional<Board> byId = boardRepository.findById(path);
        List<Board> id = boardRepository.findAll(Sort.by("id").descending());

        if(all != id){
            boardService.addMember(member, byId);

            model.addAttribute("allBoard",id);
        }

        return "board/board-main";
    }*/

    @GetMapping("/board")
    public String viewBoard(@CurrentUser Member member, Model model){
        model.addAttribute(member);

        List<Board> all = boardRepository.findAll();
        List<Board> id = boardRepository.findAll(Sort.by("id").descending());

        if(all != id){
            model.addAttribute("allBoard",id);
        }

        return "board/board-main";
    }

    @GetMapping("/new-board")
    public String createBorderForm(@CurrentUser Member member, Model model){
        model.addAttribute(member);
        model.addAttribute(new BoardForm());

        return "board/board-new-main";
    }

/*    @PostMapping("/new-board")
    public String createBoard(@CurrentUser Member member, @Valid BoardForm boardForm, Errors errors,
                               Model model, RedirectAttributes attributes){
        if (errors.hasErrors()){
            return "board/board-new-main";
        }

        model.addAttribute(member);
        Board newBoard = boardService.createNewBoard(boardForm);
        attributes.addFlashAttribute("newBorderId",newBoard.getId());
        return "redirect:/board/" + newBoard.getId();

    }*/
@PostMapping("/new-board")
public String createBoard(@CurrentUser Member member, @Valid BoardForm boardForm, Errors errors,
                          Model model, RedirectAttributes attributes){
    if (errors.hasErrors()){
        return "board/board-new-main";
    }

    model.addAttribute(member);
    Board newBoard = boardService.createNewBoard(boardForm);
    attributes.addFlashAttribute("newBorderId",newBoard.getId());
    return "redirect:/board";

}

    @GetMapping("/view-board/{path}")
    public String viewBoard(@CurrentUser Member member, @PathVariable Long path, Model model){
        model.addAttribute(member);
        Optional<Board> byId = boardRepository.findById(path);
        if(byId.isEmpty()){
            new IllegalArgumentException(path.toString());
        }


        model.addAttribute("title",byId.stream().map(Board::getTitle).collect(Collectors.joining()));
        model.addAttribute("description",byId.stream().map(Board::getDescription).collect(Collectors.joining()));
        model.addAttribute("time",byId.stream().map(Board::getAtWriting).collect(Collectors.toList()));

        return "/board/view";
    }

    @PostMapping("/new-board/add")
    @ResponseBody
    private ResponseEntity updateComment(@RequestBody CommentForm commentForm, @CurrentUser Member member){
        String reply = commentForm.getReply();

        boardService.addReply(reply,member);


        return ResponseEntity.ok().build();
    }

}
