package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.repository.BoardFileRepository;
import com.example.board.repository.BoardRepository;
import com.example.board.util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
        // 리스트 객체이기 때문에 인덱스 번으로 비교해봐야함
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            // 첨부파일 없음
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            return boardRepository.save(boardEntity).getId();
        } else {
            // 첨부파일 있음
            BoardEntity boardEntity = BoardEntity.toSaveEntityWithFile(boardDTO);
            // 게시글 저장처리 후 저장한 엔티티 가져옴(jpa 특징때문에 바로 id 값을 저장하면안됨)
            BoardEntity savedEntity = boardRepository.save(boardEntity);
            // 파일 이름 처리, 파일 로컬에 저장 등
            // DTO에 담긴 파일리스트 꺼내기
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                // 업로드한 파일 이름
                String originalFilename = boardFile.getOriginalFilename();
                //저장용 파일 이름
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                // 저장경로+ 파일이름 준비
                String savePath = "C:\\springboot_img\\" + storedFileName;
                // 파일 폴더에 저장
                boardFile.transferTo(new File(savePath));
                // 파일 정보 board_file_table 에 저장
                // 파일 정보 저장을 위한 BoardFileEntity 생성(여기서 board_id 를 위해서 부모엔티티값을 가지고 감)
                BoardFileEntity boardFileEntity =
                        BoardFileEntity.toSaveBoardFile(savedEntity, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
            return savedEntity.getId();
        }

    }

    public Page<BoardDTO> findAll(int page, String type, String q) {
        // findall 호출 시 정렬 하려는 조건을 설정해줄 수 있음
        // 괄호가 여러개있을때 괄호에 많이 쌓여져있는 부분부터 실행된다.
//        for(BoardEntity boardEntity : boardEntityList){
//            BoardDTO boardDTO = BoardDTO.toSaveDTO(boardEntity);
//            boardDTOList.add(boardDTO);
//        }
        page = page - 1;
        int pageLimit = 5;
//        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardEntity> boardEntities = null;
        // 검색인지 구분
        if (q.equals("")) {
            // 일반 페이징
            boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            if (type.equals("boardTitle")) {
                boardEntities = boardRepository.findByBoardTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            } else if (type.equals("boardWriter")) {
                boardEntities = boardRepository.findByBoardWriterContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            }
        }

        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
                BoardDTO.builder()
                        .id(boardEntity.getId())
                        .boardTitle(boardEntity.getBoardTitle())
                        .boardWriter(boardEntity.getBoardWriter())
                        .boardHits(boardEntity.getBoardHits())
                        .createdAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()))
                        .build());
        return boardList;
    }
    //조회수

    /**
     * 서비스 클래스 매서드에서 @Transactional 붙이는 경우
     * 1. jqpl 로 작성한 매서드 호출할 때
     * 2. 부모엔티티에서 자식엔티티를 바로 호출할 때
     */
    @Transactional
    public void increaseHits(Long id) {
        boardRepository.increaseHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return BoardDTO.toSaveDTO(boardEntity);
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }


    public void update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
    }
}
