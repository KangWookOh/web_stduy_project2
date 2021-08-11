package com.example.web_stduy_project2.repository;

import com.example.web_stduy_project2.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
