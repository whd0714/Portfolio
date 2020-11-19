package com.portfolio.repository;

import com.portfolio.domain.Release;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ReleaseRepositoryQueryDSL {

    List<Release> findByWord(String word);

}
