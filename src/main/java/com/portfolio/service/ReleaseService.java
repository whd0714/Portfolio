package com.portfolio.service;

import com.portfolio.controller.release.ReleaseForm;
import com.portfolio.controller.release.StoreKeywordForm;
import com.portfolio.domain.Member;
import com.portfolio.domain.Release;
import com.portfolio.domain.Shoe;
import com.portfolio.domain.Store;
import com.portfolio.repository.ReleaseRepository;
import com.portfolio.repository.ShoeRepository;
import jdk.jfr.Frequency;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReleaseService {

    private final ReleaseRepository releaseRepository;
    private final ShoeRepository shoeRepository;

    public Release newReleaseInfo(ReleaseForm releaseForm, Member member) {
        LocalDate localDate = LocalDate.parse(releaseForm.getReleaseTime());
        Release release = Release.builder()
                .title(releaseForm.getTitle())
                .brand(releaseForm.getBrand())
                .modelNo(releaseForm.getModelNo())
                .releaseTime(localDate)
                .build();

        releaseRepository.save(release);

        Shoe shoe = Shoe.builder()
                .brand(release.getBrand())
                .modelNo(release.getModelNo())
                .build();
        shoeRepository.save(shoe);
        return release;
    }

    public List<Store>  getStore(Release byModelNo) {
        Optional<Release> byId = releaseRepository.findById(byModelNo.getId());
        return byId.orElseThrow().getStores();
    }


    public void updateStoreKeyword(Release release, Store store) {
        Optional<Release> byId = releaseRepository.findById(release.getId());
        byId.ifPresent(a -> a.getStores().add(store));
    }
}
