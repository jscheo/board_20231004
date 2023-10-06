package com.example.board.dto;

import com.example.board.entity.CommentEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;

    public static CommentDTO toSaveDTO(CommentEntity saveComment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(saveComment.getId());
        commentDTO.setCommentWriter(saveComment.getCommentWriter());
        commentDTO.setCommentContents(saveComment.getCommentContents());
        return commentDTO;
    }

    public static List<CommentDTO> toSaveDTOList(List<CommentEntity> commentEntityList) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(commentEntity.getId());
            commentDTO.setCommentWriter(commentEntity.getCommentWriter());
            commentDTO.setCommentContents(commentEntity.getCommentContents());
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
