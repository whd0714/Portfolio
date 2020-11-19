package com.portfolio.controller.main;

import com.portfolio.controller.member.CurrentUser;
import com.portfolio.domain.Member;
import com.portfolio.domain.Release;
import com.portfolio.repository.ReleaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ReleaseRepository releaseRepository;

    @GetMapping("/")
    public String home(@CurrentUser Member member, Model model){
        if(member != null){
            model.addAttribute(member);
        }

        return "/index";
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/search/release")
    public String searchRelease(String word, Model model){
        List<Release> words = releaseRepository.findByWord(word);
        model.addAttribute( words);
        model.addAttribute("word",word);
        return "search";
    }

}
