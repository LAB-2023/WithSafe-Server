package com.withsafe.domain.indoorMap.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.withsafe.domain.beacon.dto.BeaconDto;
import com.withsafe.domain.indoorEntrance.dto.IndoorEntranceDto;
import com.withsafe.domain.indoorMap.domain.IndoorMap;
import com.withsafe.domain.indoorMap.domain.QIndoorMap;
import com.withsafe.domain.indoorMap.dto.IndoorMapDto;
import com.withsafe.domain.indoorMap.dto.IndoorMapDto.IndoorMapLocationInfo;
import com.withsafe.domain.indoorMap.dto.IndoorMapDto.SearchCondition;
import com.withsafe.domain.restrictArea.domain.QRestrictArea;
import com.withsafe.domain.restrictArea.domain.RestrictArea;
import com.withsafe.domain.restrictArea.dto.RestrictAreaDto;
import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.withsafe.domain.beacon.domain.QBeacon.beacon;
import static com.withsafe.domain.beacon.dto.BeaconDto.*;
import static com.withsafe.domain.department.domain.QDepartment.department;
import static com.withsafe.domain.indoorEntrance.domain.QIndoorEntrance.indoorEntrance;
import static com.withsafe.domain.indoorEntrance.dto.IndoorEntranceDto.*;
import static com.withsafe.domain.indoorMap.domain.QIndoorMap.*;
import static com.withsafe.domain.indoorMap.dto.IndoorMapDto.*;
import static com.withsafe.domain.restrictArea.domain.QRestrictArea.restrictArea;
import static com.withsafe.domain.restrictArea.dto.RestrictAreaDto.*;
import static com.withsafe.domain.user.domain.QUser.user;
import static com.withsafe.domain.watch.domain.QWatch.watch;

@Repository
public class IndoorMapLocationRepositoryImpl extends QuerydslRepositorySupport implements IndoorMapLocationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public IndoorMapLocationRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(IndoorMap.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 다시 해봐야할듯
     * @param searchCondition
     * @return
     */
    @Override
    public IndoorMapLocationResponse findAllIndoorMapInfo(SearchCondition searchCondition) {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JtsModule());
        IndoorMap findMap = jpaQueryFactory
                .selectFrom(indoorMap)
                .join(indoorMap.department, department).fetchJoin()
                .where(eqMapId(searchCondition.getIndoorMapId()), eqDepartmentName(searchCondition.getDepartmentName()))
                .fetchOne();
        List<RestrictFind> restrictFindList =
                jpaQueryFactory.select(
                            Projections.fields(
                                    RestrictFind.class,
                                restrictArea.id.as("restrictAreaId"),
                                    ExpressionUtils.as(restrictArea.coordinate_left, "restrictAreaCoordinateLeft"),
                                    ExpressionUtils.as(restrictArea.coordinate_right, "restrictAreaCoordinateRight")
                            )
                        )
                        .from(restrictArea)
                        //.where(eqMapId(findMap.getId()))
                        .fetch();
//        List<BeaconFind> beaconFindList =
//                jpaQueryFactory.select(
//                        Projections.fields(
//                                BeaconFind.class,
//                                beacon.id.as("beaconId"),
//                                beacon.coordinate.as("beaconCoordinate"))
//                        )
//                        .from(beacon)
//                        .where(eqMapIdBeacon(findMap.getId()))
//                        .fetch();
//        List<Long> beaconIds = beaconFindList.stream()
//                .map(BeaconFind::getBeaconId)
//                .collect(Collectors.toList());
//        List<IndoorEntranceResponse> indoorEntranceResponseList =
//                jpaQueryFactory.select(
//                        Projections.fields(
//                                IndoorEntranceResponse.class,
//                                indoorEntrance.watch.id.as("watchId"))
//                        )
//                        .from(indoorEntrance)
//                        .join(indoorEntrance.watch, watch)
//                        .where(eqBeaconId(beaconIds))
//                        .fetch();
        IndoorMapLocationResponse result =
                IndoorMapLocationResponse.builder()
                        .indoorMapId(findMap.getId())
                        .departmentName(findMap.getDepartment().getName())
                        .restrict(restrictFindList)
                        //.beacon(beaconFindList)
                        .build();
        return result;
    }

    @Override
    public List<IndoorMapLocationInfo> findAllBySearchCondition(SearchCondition searchCondition) {

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JtsModule());

        List<Tuple> indoorMapLocationList = jpaQueryFactory.select(
                        department.name, indoorMap.id,
                        restrictArea.id, restrictArea.coordinate_left, restrictArea.coordinate_right,
                        beacon.id, beacon.coordinate, indoorEntrance.id,watch.id,
                        user.id,user.name, user.phoneNum)
                .from(indoorMap)
                .innerJoin(indoorMap.department, department)
                .leftJoin(indoorMap.restrictAreaList, restrictArea)
                .leftJoin(indoorMap.beaconList, beacon)
                .leftJoin(beacon.indoorEntranceList,indoorEntrance)
                .leftJoin(indoorEntrance.watch, watch)
                .leftJoin(watch.user,user)
//                .where(eqUserName(searchCondition.getUserName())
//                        .or(eqDeviceNum(searchCondition.getDeviceNum())))
                .where(eqDepartmentName(searchCondition.getDepartmentName())
                        ,eqUserName(searchCondition.getUserName())
                        ,eqDeviceNum(searchCondition.getDeviceNum())
                        ,eqMapId(searchCondition.getIndoorMapId())
                )
                .fetch();


        List<IndoorMapLocationInfo> indoorMapLocationInfoList = indoorMapLocationList.stream()
                .map(tuple -> {
                    try {
                        return IndoorMapLocationInfo.toIndoorMapLocationInfo(
                                tuple.get(department.name),
                                tuple.get(indoorMap.id),
                                tuple.get(restrictArea.id),
                                objectMapper.writeValueAsString(tuple.get(restrictArea.coordinate_left)),
                                objectMapper.writeValueAsString(tuple.get(restrictArea.coordinate_right)),
                                tuple.get(beacon.id),
                                objectMapper.writeValueAsString(tuple.get(beacon.coordinate)),
                                tuple.get(indoorEntrance.id),
                                tuple.get(watch.id),
                                tuple.get(user.id),
                                tuple.get(user.name),
                                tuple.get(user.phoneNum)
                        );
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());


        return indoorMapLocationInfoList;
    }


    //부서 이름 비교
    private BooleanExpression eqDepartmentName(String departmentName){
        if(departmentName == null || departmentName.equals("")){
            return null;
        }
        else
            return department.name.eq(departmentName);
    }

    //사용자 이름 비교
    private BooleanExpression eqUserName(String userName){
        if(userName == null || userName.equals("")){
            return null;
        }
        else
            return user.name.eq(userName);
    }

    //디바이스 번호 비교
    private BooleanExpression eqDeviceNum(Integer deviceNum){
        if(deviceNum == null || deviceNum.equals(0)){
            return null;
        }
        else
            return watch.deviceNum.eq(deviceNum);
    }

    //맵 아이디 비교
    private BooleanExpression eqMapId(Long indoorMapId){
        if(indoorMapId == null)
            return null;
        else return indoorMap.id.eq(indoorMapId);
    }

    //비콘 and 맵 아이디 비교
    private BooleanExpression eqMapIdBeacon(Long indoorMapId){
        if(indoorMapId == null)
            return null;
        else return beacon.id.eq(indoorMapId);
    }

    //비콘 아이디 비교
    private BooleanExpression eqBeaconId(List<Long> beaconId){
        if(beaconId == null)
            return null;
        else return indoorMap.id.in(beaconId);
    }
}
