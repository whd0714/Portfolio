package com.portfolio.controller.release;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.controller.member.CurrentUser;
import com.portfolio.domain.Member;
import com.portfolio.domain.Release;
import com.portfolio.domain.Store;
import com.portfolio.repository.ReleaseRepository;
import com.portfolio.repository.StoreRepository;
import com.portfolio.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReleaseController {

    private final ReleaseService releaseService;
    private final ReleaseRepository releaseRepository;
    private final ObjectMapper objectMapper;
    private final StoreRepository storeRepository;

    @GetMapping("/release")
    public String releaseForm(@CurrentUser Member member, Model model){
        model.addAttribute(member);
        model.addAttribute(new ReleaseForm());

        return "release/form";
    }

    @PostMapping("/release")
    public String release(@CurrentUser Member member, @Valid ReleaseForm releaseForm, Errors errors){
        if (errors.hasErrors()){
            return "release/form";
        }

        Release newRelease = releaseService.newReleaseInfo(releaseForm, member);
        return "redirect:/release/" + URLEncoder.encode(newRelease.getModelNo(),StandardCharsets.UTF_8);
    }

    @GetMapping("/release/{path}")
    public String viewRelease(@CurrentUser Member member, @PathVariable String path, Model model){
        model.addAttribute(member);

        Release byModelNo = releaseRepository.findByModelNo(path);
        List<Store> store = releaseService.getStore(byModelNo);


        model.addAttribute("storeList", store.stream().map(Store::toString).collect(Collectors.toList()));

        if(byModelNo != null){
            model.addAttribute(byModelNo);
        }
        model.addAttribute("storeNameList",byModelNo.getStores().stream().map(Store::getStoreName).collect(Collectors.toList()));
        return "release/view";
    }

    @GetMapping("/release/{path}/setting")
    public String viewReleaseSetting(@CurrentUser Member member, @PathVariable String path, Model model) throws JsonProcessingException {

        model.addAttribute(member);

        Release release = releaseRepository.findByModelNo(path);

        List<Store> store = releaseService.getStore(release);
        String pathName = release.getModelNo();
        model.addAttribute("pathName",URLEncoder.encode(pathName, StandardCharsets.UTF_8));
        model.addAttribute(release);
        model.addAttribute("storeList", store.stream().map(Store::toString).collect(Collectors.toList()));
        List<String> collect = storeRepository.findAll().stream().map(Store::toString).collect(Collectors.toList());
        model.addAttribute("whiteList",objectMapper.writeValueAsString(collect));

        return "release/setting";
    }

    @PostMapping("/release/{path}/setting/add")
    @ResponseBody
    public ResponseEntity updateStoreKeyword(@CurrentUser Member member, @PathVariable String path, Model model,
                                             @RequestBody StoreKeywordForm storeKeywordForm){

        model.addAttribute(member);
        Store store = storeRepository.findByStoreName(storeKeywordForm.getStoreName());
        Release release = releaseRepository.findByModelNo(path);

        releaseService.updateStoreKeyword(release, store);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/release/{path}/setting/form")
    public String updateReleaseForm(@CurrentUser Member member, @PathVariable String path, Model model){
        model.addAttribute(member);

        Release release = releaseRepository.findByModelNo(path);
        model.addAttribute(new ReleaseForm(release));
        model.addAttribute(release);
        return "release/setting-form";
    }

}
