package com.example.board.repository;

import com.example.board.entity.BaseEntity;
import com.example.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardEntityOrderByIdDesc(BaseEntity baseEntity);
}
