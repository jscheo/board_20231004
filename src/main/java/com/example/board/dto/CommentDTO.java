package com.example.board.dto;

import com.example.board.entity.CommentEntity;
import com.example.board.util.UtilClass;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private String createdAt;
    private String updatedAt;

    public static CommentDTO toSaveDTO(CommentEntity saveComment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(saveComment.getId());
        commentDTO.setCommentWriter(saveComment.getCommentWriter());
        commentDTO.setCommentContents(saveComment.getCommentContents());
        commentDTO.setBoardId(saveComment.getBoardEntity().getId());
        commentDTO.setCreatedAt(UtilClass.dateTimeFormat(saveComment.getCreatedAt()));
        commentDTO.setUpdatedAt(UtilClass.dateTimeFormat(saveComment.getUpdatedAt()));
        return commentDTO;
    }


}
