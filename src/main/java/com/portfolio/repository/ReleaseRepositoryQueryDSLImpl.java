package com.portfolio.repository;

import com.portfolio.domain.QKeyword;
import com.portfolio.domain.QRelease;
import com.portfolio.domain.Release;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ReleaseRepositoryQueryDSLImpl extends QuerydslRepositorySupport implements ReleaseRepositoryQueryDSL{

    public ReleaseRepositoryQueryDSLImpl() {
        super(Release.class);
    }

    @Override
    public List<Release> findByWord(String word) {
        QRelease release = QRelease.release;
        /*JPQLQuery<Release> query = from(release).where(release.open.isTrue()
                .and(release.title.containsIgnoreCase(word))
                .or(release.keywords.any().title.containsIgnoreCase(word)));*/

        JPQLQuery<Release> query = from(release).where(release.open.isTrue()
                .and(release.brand.containsIgnoreCase(word))
                .or(release.modelNo.containsIgnoreCase(word))
                .or(release.keywords.any().title.containsIgnoreCase(word)))
                .leftJoin(release.keywords, QKeyword.keyword);

        return query.fetch();
    }
}
