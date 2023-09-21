package com.withsafe.domain.area;

import com.withsafe.domain.Department;
import com.withsafe.domain.device.EnvSensor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class OutdoorMap {

    @Id @GeneratedValue
    @Column(name = "outdoor_map_id")
    private Long id;

    private String name;
    private Point coordinate;

    //연관관계 매핑
    @OneToMany
    private List<RestrictArea> restrictAreas = new ArrayList<>();
    @OneToMany
    private List<EnvSensor> envSensors = new ArrayList<>();
    @ManyToOne
    private Department department;
}
