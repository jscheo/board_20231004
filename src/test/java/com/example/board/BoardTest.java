package com.example.board;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;
import com.example.board.util.UtilClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    private BoardDTO newBoard(int i) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardWriter("test_writer" + i);
        boardDTO.setBoardTitle("test_title" + i);
        boardDTO.setBoardPass("1234" + i);
        boardDTO.setBoardContents("test" + i);
        return boardDTO;
    }

    // 게시글 50 개 저장하기
    @Test
    @DisplayName("게시글 데이터 붓기")
    public void boardInsert() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            boardService.save(newBoard(i));
        });
    }

    @Test
    @DisplayName("페이징 객체 확인")
    public void pagingMethod() {
        int page = 0;
        int pageLimit = 5;
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막페이지인지 여부

        // Page<BoardEntity> -> Page<BoardDTO>
        // 임의로 바꿔주게 되면 매서드를 활용하지 못하기 때문에 map을 이용해서 변환해야한다.
        // Page<BoardEntity> -> Page<BoardDTO>
        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
                BoardDTO.builder()
                        .id(boardEntity.getId())
                        .boardTitle(boardEntity.getBoardTitle())
                        .boardWriter(boardEntity.getBoardWriter())
                        .boardHits(boardEntity.getBoardHits())
                        .createdAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()))
                        .build());
        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부
    }

    @Test
    @DisplayName("검색 매서드 확인")
    public void searchMethod(){
        //제목에 1이 포함된 결과 검색
//        List<BoardEntity> boardEntityList = boardRepository.findByBoardTitleContaining("1");

        // 제목에 1이 포함된 결과 페이징
        int page = 0;
        int pageLimit = 5;
        Page<BoardEntity> boardEntities =
                boardRepository.findByBoardTitleContaining("1", PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        // 제목 또는 작성자에 1이 포함된 결과 페이징
        String q = "1";
        boardEntities = boardRepository.findByBoardTitleContainingOrBoardWriterContaining(q,q,PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // BoardEntitiy를 출력하지 않고 가격을 DTO로 변환해서 출력
//        boardEntityList.forEach(boardEntity -> {
//            System.out.println(BoardDTO.toSaveDTO(boardEntity));
//        });

        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
                BoardDTO.builder()
                        .id(boardEntity.getId())
                        .boardTitle(boardEntity.getBoardTitle())
                        .boardWriter(boardEntity.getBoardWriter())
                        .boardHits(boardEntity.getBoardHits())
                        .createdAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()))
                        .build());
        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부
    }
}

