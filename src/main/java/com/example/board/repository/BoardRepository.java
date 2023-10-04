package com.example.board.repository;


import com.example.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    /*
        update board_table set board_hits + 1 where id = ?
        jpql(java persistence query language)
     */
    @Modifying // insert, update, delete 를 하고자 할 때
    // query 문은 Entity에 작성된 내용을 기준으로        b.id:id 가 Param에 들어가는 것이다.
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits +1 where b.id=:id")
    // db 에 실제로 사용되는 query 문법을 nativequery라고 한다.
//    @Query(value = "update board_table set board_hits + 1 where id = :id", nativeQuery = true)
    void increaseHits(@Param("id") Long id);
}
