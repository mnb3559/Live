package com.ssafy.live.house.controller.dto;

import com.ssafy.live.common.domain.Entity.item.Direction;
import com.ssafy.live.common.domain.Entity.item.Entrance;
import com.ssafy.live.common.domain.Entity.item.Heating;
import com.ssafy.live.house.domain.entity.House;
import com.ssafy.live.house.domain.entity.Item;
import com.ssafy.live.house.domain.entity.ItemImage;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ItemDetailResponse{

        private Long itemNo;
        private Long realtorNo;
        private int deposit;
        private int rent;
        private int maintenanceFee;
        private String description;
        private LocalDate moveInDate;
        private Heating heating;
        private Direction direction;
        private Entrance entrance;
        private List<ItemImageResponse> itemImages;
        private HouseResponse.HouseDetailResponse house;
        private ItemOptionResponse.ItemOptionDetailResponse itemOption;


        public static ItemDetailResponse toDto(Item item) {
            List<ItemImageResponse> imgs = new ArrayList<>();
            for(ItemImage img : item.getItemImages()){
                imgs.add(ItemImageResponse.toDto(img));
            }
            return ItemDetailResponse.builder()
                    .house(HouseResponse.HouseDetailResponse.toDto(item.getHouse()))
                    .itemOption(ItemOptionResponse.ItemOptionDetailResponse.toDto(item.getItemOption()))
                    .itemNo(item.getNo())
                    .realtorNo(item.getRealtor().getNo())
                    .deposit(item.getDeposit())
                    .rent(item.getRent())
                    .maintenanceFee(item.getMaintenanceFee())
                    .description(item.getDescription())
                    .moveInDate(item.getMoveInDate())
                    .heating(item.getHeating())
                    .direction(item.getDirection())
                    .entrance(item.getEntrance())
                    .itemImages(imgs)
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class ItemSimpleResponse {
        private Long itemNo;
        private Long realtorNo;
        private int deposit;
        private int rent;
        private int maintenanceFee;
        private String description;
        private String buildingName;
        private String image;
        private String address;
        private String addressDetail;

        public static ItemSimpleResponse toDto(Item item) {
            return ItemSimpleResponse.builder()
                    .itemNo(item.getNo())
                    .realtorNo(item.getRealtor().getNo())
                    .deposit(item.getDeposit())
                    .rent(item.getRent())
                    .maintenanceFee(item.getMaintenanceFee())
                    .description(item.getDescription())
                    .image(item.getItemImages().get(0).getImageSrc())
                    .address(item.getHouse().getAddress())
                    .addressDetail(item.getHouse().getAddressDetail())
                    .build();
        }
    }
    
}