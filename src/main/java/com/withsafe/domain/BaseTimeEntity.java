package com.withsafe.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity{

    // Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
    // 조회한 Entity 값을 변경할 때 시간이 자동 저장됩니다.
    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime modifiedDate;

//    public BaseTimeEntity(LocalDateTime createdDate, LocalDateTime modifiedDate) {
//        this.createdDate = createdDate;
//        this.modifiedDate = modifiedDate;
//    }
}
