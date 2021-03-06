package com.portfolio.repository;

import com.portfolio.domain.Member;
import com.portfolio.domain.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, Long>, ReleaseRepositoryQueryDSL {

    Release findByModelNo(String path);



}
