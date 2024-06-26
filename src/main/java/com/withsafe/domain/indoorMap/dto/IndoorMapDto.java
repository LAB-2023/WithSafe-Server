package com.withsafe.domain.indoorMap.dto;

import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

public class IndoorMapDto {

    //해당 부서의 실내 지도를 저장하는 클래스
    //실내 지도의 디테일한 정보 x, 실내 지도에 뭐가 있는지
    //ex. ai관 3층, ai관 4층, 가천관 3층
    @Builder
    @Getter
    public static class IndoorMapInfo {
        private Long id;
        private String name;
        private String imageURL;

        public static IndoorMapInfo toIndoorMapInfo(Long id, String name, String imageURL) {
            return IndoorMapInfo.builder()
                    .id(id)
                    .name(name)
                    .imageURL(imageURL)
                    .build();
        }
    }

    //특정 실내 지도에 대한 디테일한 정보

    /**
     * userName, phoneNum은 당장 필요 없으니 필요할 때 가져오는게 맞음
     */
    @Builder
    @Getter
    public static class IndoorMapLocationResponse<T>{
        private Long indoorMapId;
        private String departmentName;
        private T restrict;
        private T beacon;
    }

    @Builder
    @Getter
    public static class IndoorMapLocationInfo{

        private String departmentName;
        private Long indoorMapId;
        private Long restrictAreaId;
        private String restrictAreaCoordinateLeft;
        private String restrictAreaCoordinateRight;
        private Long beaconId;
        private String beaconCoordinate;
        private Long indoorEntranceId;
        private Long watchId;
        private Long userId;
        private String userName;
        private String phoneNum;

        public static IndoorMapLocationInfo toIndoorMapLocationInfo(String departmentName,Long indoorMapId,
                                     Long restrictAreaId, String restrictAreaCoordinateLeft, String restrictAreaCoordinateRight,
                                     Long beaconId, String beaconCoordinate,
                                     Long indoorEntranceId, Long watchId,
                                     Long userId, String userName, String phoneNum) {

            return IndoorMapLocationInfo.builder()
                        .departmentName(departmentName)
                        .indoorMapId(indoorMapId)
                        .restrictAreaId(restrictAreaId)
                        .restrictAreaCoordinateLeft(restrictAreaCoordinateLeft)
                        .restrictAreaCoordinateRight(restrictAreaCoordinateRight)
                        .beaconId(beaconId)
                        .beaconCoordinate(beaconCoordinate)
                        .indoorEntranceId(indoorEntranceId)
                        .watchId(watchId)
                        .userId(userId)
                        .userName(userName)
                        .phoneNum(phoneNum)
                        .build();
        }
    }

    @Getter
    @Builder
    public static class SearchCondition{
        private String departmentName;
        private Long indoorMapId;
        private String userName;
        private Integer deviceNum;

        public static SearchCondition toSearchCondition(String departmentName, Long indoorMapId, String userName, Integer deviceNum) {
            return SearchCondition.builder()
                    .departmentName(departmentName)
                    .indoorMapId(indoorMapId)
                    .userName(userName)
                    .deviceNum(deviceNum)
                    .build();
        }
    }


}
