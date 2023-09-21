package com.withsafe.domain.area;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class RestrictArea {
    @Id @GeneratedValue
    @Column(name = "restrict_area_id")
    private Long id;

    private String name;

    private String safetyRule;

    private Point coordinate;

    //연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indoor_map_id")
    private IndoorMap indoorMap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outdoor_map_id")
    private OutdoorMap outdoorMap;
}