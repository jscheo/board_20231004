package com.example.board.service;

import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.CommentEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    public CommentDTO save(CommentDTO commentDTO, Long id) {
        Optional<BoardEntity> byId = boardRepository.findById(id);
        BoardEntity boardEntity = byId.get();
        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
        CommentEntity saveComment = commentRepository.save(commentEntity);
        return CommentDTO.toSaveDTO(saveComment);
    }


    public List<CommentDTO> findAll() {
        List<CommentEntity> commentEntityList = commentRepository.findAll();
        List<CommentDTO> commentDTOList = CommentDTO.toSaveDTOList(commentEntityList);
        return commentDTOList;
    }
}
