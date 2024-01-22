package com.withsafe.domain.indoorMap.dao;


import com.querydsl.core.Tuple;
import com.withsafe.domain.indoorMap.dto.IndoorMapDto;

import java.util.List;

import static com.withsafe.domain.indoorMap.dto.IndoorMapDto.*;

public interface IndoorMapLocationRepository {
    List<IndoorMapLocationInfo> findAllBySearchCondition(SearchCondition searchCondition);

    IndoorMapLocationResponse findAllIndoorMapInfo(SearchCondition searchCondition);
}

