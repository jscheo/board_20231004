package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Long save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        return boardRepository.save(boardEntity).getId();
    }

    public List<BoardDTO> findAll() {
        // findall 호출 시 정렬 하려는 조건을 설정해줄 수 있음
        List<BoardEntity> boardEntityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardEntityList.forEach(board -> {
            boardDTOList.add(BoardDTO.toSaveDTO(board));
        });
        // 괄호가 여러개있을때 괄호에 많이 쌓여져있는 부분부터 실행된다.

//        for(BoardEntity boardEntity : boardEntityList){
//            BoardDTO boardDTO = BoardDTO.toSaveDTO(boardEntity);
//            boardDTOList.add(boardDTO);
//        }
        return boardDTOList;
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

    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return BoardDTO.toSaveDTO(boardEntity);
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }


}
