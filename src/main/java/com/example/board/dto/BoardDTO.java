package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import lombok.Data;

@Data
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPass;
    private String boardContents;
    private int boardHits;

    public static BoardDTO toSaveDTO(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        return boardDTO;
    }
}
