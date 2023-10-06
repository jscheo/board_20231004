package com.example.board.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@Table(name="board_file_table")
public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;
    // 일대다/ 다대일 등의 설정은 annotation을 쓰는 엔티티 기준으로 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // DB에 생성될 참조 컬럼의 이름// 어떤 칼럼으로 조인할거냐
    private BoardEntity boardEntity; // 부모 엔티티 타입으로 정의

    public static BoardFileEntity toSaveBoardFile(BoardEntity savedEntity, String originalFilename, String storedFileName) {
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileName(originalFilename);
        boardFileEntity.setStoredFileName(storedFileName);
        // DB 에서는 id 값만 저장이 된다.
        boardFileEntity.setBoardEntity(savedEntity);
        return boardFileEntity;
    }
}
