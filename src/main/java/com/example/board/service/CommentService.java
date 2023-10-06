package com.example.board.service;

import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.CommentEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    public Long save(CommentDTO commentDTO) {
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
        return commentRepository.save(commentEntity).getId();
    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException());
        List<CommentEntity> commentEntityList = commentRepository.findByBoardEntityOrderByIdDesc(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentEntityList.forEach(comment ->{
            commentDTOList.add(CommentDTO.toSaveDTO(comment));
        });
        return commentDTOList;
    }
}
