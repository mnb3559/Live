package com.ssafy.live.house.service;

import com.ssafy.live.account.auth.jwt.JwtTokenProvider;
import com.ssafy.live.account.common.service.S3Service;
import com.ssafy.live.account.realtor.domain.entity.Realtor;
import com.ssafy.live.account.realtor.domain.repository.RealtorRepository;
import com.ssafy.live.common.domain.Response;
import com.ssafy.live.common.domain.exception.NotFoundException;
import com.ssafy.live.common.domain.exception.UnauthorizedException;
import com.ssafy.live.house.controller.dto.ItemRequest;
import com.ssafy.live.house.controller.dto.ItemResponse;
import com.ssafy.live.house.domain.entity.House;
import com.ssafy.live.house.domain.entity.Item;
import com.ssafy.live.house.domain.entity.ItemImage;
import com.ssafy.live.house.domain.entity.ItemOption;
import com.ssafy.live.house.domain.repository.HouseRepository;
import com.ssafy.live.house.domain.repository.ItemImageRepository;
import com.ssafy.live.house.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ssafy.live.common.domain.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemImageRepository itemImageRepository;


    private final S3Service s3Service;
    private final Response response;
    private final ItemRepository itemRepository;
    private final HouseRepository houseRepository;
    private final RealtorRepository realtorRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> registItem(ItemRequest.ItemRegistRequest itemRegistRequest, List<MultipartFile> files) throws IOException {

        Realtor realtor = realtorRepository.findById(itemRegistRequest.getRealtorNo())
                .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));

        Long houseNo = 0L;
        House house = null;
        houseNo = itemRegistRequest.getHouse().getHouseNo();
        if(houseNo!=null) house = houseRepository.findById(houseNo).orElse(null);
        if(house==null) {
            house = itemRegistRequest.getHouse().toEntity();
            houseRepository.save(house);
        }

        Item item = itemRegistRequest.toEntity(realtor, house);
        ItemOption itemOption = itemRegistRequest.getItemOption().toEntity(item.getNo());
        itemOption.setItem(item);
        item.setOption(itemOption);

        List<ItemImage> itemImages = new ArrayList<>();
        for(MultipartFile file : files){
            String imageSrc = s3Service.upload(file);
            ItemImage itemImage = ItemImage.builder()
                    .item(item)
                    .imageSrc(imageSrc)
                    .build();
            itemImages.add(itemImage);
        }
        item.setItemImages(itemImages);


        itemRepository.save(item);
        return response.success("매물이 등록되었습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> findItemDetail(Long itemNo) {
        Item item = itemRepository.findById(itemNo)
                .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));

        ItemResponse.ItemDetailResponse itemDetailResponse = ItemResponse.ItemDetailResponse.toDto(item);
        return response.success(itemDetailResponse, "매물 상세 정보가 조회되었습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> updateItemDetail(ItemRequest.ItemUpdateRequest itemUpdateRequest, List<MultipartFile> files) throws IOException {
        Item item = itemRepository.findById(itemUpdateRequest.getItemNo())
                .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));
        Item updatedItem = itemUpdateRequest.toEntity();

        List<ItemImage> itemImages = itemImageRepository.findAllByItemNo(item.getNo());
        Set<Long> newImageNoSet = itemUpdateRequest.getItemImages();
        for(ItemImage img : itemImages){
            if(!newImageNoSet.contains(img.getNo())){
                s3Service.deleteFile(img.getImageSrc());
                itemImageRepository.deleteById(img.getNo());
            }
        }

        for(MultipartFile file : files){
            String imageSrc = s3Service.upload(file);
            ItemImage itemImage = ItemImage.builder()
                    .item(item)
                    .imageSrc(imageSrc)
                    .build();
            itemImages.add(itemImage);
        }
        item.setItemImages(itemImages);

        itemRepository.save(updatedItem);
        return response.success("매물 정보가 수정되었습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> itemsByBuildingName(String token, ItemRequest.ItemsByBuildingName request) {
        if(!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException(ACCESS_TOKEN_EXPIRED);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        List<ItemResponse.ItemsByBuildingName> list = new ArrayList<>();
        Realtor realtor = realtorRepository.findByBusinessNumber(authentication.getName())
                .orElseThrow(() -> new NotFoundException(REALTOR_NOT_FOUND));

        List<Item> items = itemRepository.findByRealtorLikeBuildingName(request.getWord(), realtor.getNo(), request.getRegionCode());
        items.stream()
                .forEach(item -> {
                    House house = item.getHouse();
                    String image = itemImageRepository.findTop1ByItemNo(item.getNo()).getImageSrc();
                    list.add(ItemResponse.ItemsByBuildingName.toEntity(item, house, image));
                });
        return response.success(list, "매물 목록이 조회되었습니다.", HttpStatus.OK);
    }
}
