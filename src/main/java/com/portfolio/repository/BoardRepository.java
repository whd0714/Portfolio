package com.portfolio.repository;

import com.portfolio.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findByTitle(String title);
}
