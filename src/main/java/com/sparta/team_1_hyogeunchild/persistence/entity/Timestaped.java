package com.sparta.team_1_hyogeunchild.persistence.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter             //get함수를 자동으로 생성한다.
@MappedSuperclass   //멤버 변수가 컬럼이 되도록합니다.
@EntityListeners(AuditingEntityListener.class)//변경 되었을때 자동으로 기록합니다.
public class Timestaped {
    @CreatedDate//최소 생성시점
    private LocalDateTime createdDate;
    @LastModifiedDate//마지막 변경시점
    private LocalDateTime modifiedDate;
}
