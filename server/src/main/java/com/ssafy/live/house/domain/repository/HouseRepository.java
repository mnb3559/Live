package com.ssafy.live.house.domain.repository;

import com.ssafy.live.house.domain.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    House findTop1ByAddressAndAddressDetail(String address, String addressDetail);


}
