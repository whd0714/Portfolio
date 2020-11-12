package com.portfolio.repository;

import com.portfolio.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByTitle(String title);
}
