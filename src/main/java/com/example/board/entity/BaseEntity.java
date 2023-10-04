package com.example.board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 작성시간, 수정시간 칼럼을 가지는 클래스
 * 이 클래스를 상속받는 클래스는 작성시간(createdAt), 수정시간(updatedAt) 칼럼이 추가됨
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    // 작성시간 칼럼
    @CreationTimestamp
    // 업데이트할 떄는 작동하지 않는다.
    @Column(updatable = false)
    private LocalDateTime createdAt;

    //수정시간 칼럼
    @UpdateTimestamp
    // insert 할때는 작동하지 않는다.
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
