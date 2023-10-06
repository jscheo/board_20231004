package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import com.example.board.util.UtilClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPass;
    private String boardContents;
    private String createdAt;
    private int boardHits;

    private MultipartFile boardFile;
    private int fileAttached;
    private String originalFileName;
    private String storedFileName;

    public static BoardDTO toSaveDTO(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setCreatedAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()));

        // 파일 첨부 여부에 따라 파일이름 가져가기
        if(boardEntity.getFileAttached() == 1){
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
            boardDTO.setFileAttached(1);
        }else{
            boardDTO.setFileAttached(0);
        }
        return boardDTO;

    }
}
