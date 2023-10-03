package com.withsafe.domain.watch.domain;

import com.withsafe.domain.BaseTimeEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
public class BioData extends BaseTimeEntity {  //워치에서 넘어오는 유저의 생체 데이터
    @Id @GeneratedValue
    @Column(name = "bio_data_id")
    private Long id;

    private int heartRate;     //심장박동수
    private double temperature; //체온
    //private LocalDateTime date; //데이터가 넘어오는 시간
    private int walkCount;  //걸음수
    private double oxygen;  //산소포화도

    //연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "watch_id")
    private Watch watch;
}