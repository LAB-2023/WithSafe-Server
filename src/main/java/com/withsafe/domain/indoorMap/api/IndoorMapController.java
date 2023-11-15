package com.withsafe.domain.indoorMap.api;

import com.withsafe.domain.indoorEntrance.dto.IndoorEntranceDto;
import com.withsafe.domain.indoorMap.application.IndoorMapService;
import com.withsafe.domain.indoorMap.dto.IndoorMapDto;
import com.withsafe.domain.indoorMap.dto.IndoorMapDto.IndoorMapInfo;
import com.withsafe.domain.indoorMap.dto.IndoorMapDto.IndoorMapLocationInfo;
import com.withsafe.domain.indoorMap.dto.IndoorMapDto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/indoor-map")
public class IndoorMapController {

    private final IndoorMapService indoorMapService;

    @GetMapping("/list")
    public List<IndoorMapInfo> getIndoorMapInfo(@RequestParam String departmentName){
        return indoorMapService.getIndoorMapList(departmentName);
    }

    @GetMapping("/location-info")
    public List<IndoorMapLocationInfo> getIndoorMapLocationInfo(@RequestParam String departmentName,
                                                                @RequestParam(required = false) String userName,
                                                                @RequestParam(required = false) Integer deviceNum){

        SearchCondition searchCondition = SearchCondition.toSearchCondition(departmentName, userName, deviceNum);

        return indoorMapService.getIndoorMapLocationList(searchCondition);

    }


}
