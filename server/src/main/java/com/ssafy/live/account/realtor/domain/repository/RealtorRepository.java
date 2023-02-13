package com.ssafy.live.account.realtor.domain.repository;

import com.ssafy.live.account.realtor.controller.dto.RealtorByRegionProjectionInterface;
import com.ssafy.live.account.realtor.controller.dto.RealtorProjectionInterface;
import com.ssafy.live.account.realtor.domain.entity.Realtor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtorRepository extends JpaRepository<Realtor, Long> {

    Optional<Realtor> findByBusinessNumber(String businessNumber);
    boolean existsByBusinessNumber(String businessNumber);
    Realtor findByEmailAndBusinessNumber(String email, String businessNumber);

    @Query(value = "SELECT r.name, r.image_src as imageSrc, r.corp, COUNT(v.review_no) as review, r.phone, " +
            "r.rating_score as starScore, count(i.realtor_no) as total, r.description, r.business_address as businessAddress FROM live.realtor r " +
            "left join review v on r.realtor_no=v.realtor_no " +
            "left join item i on i.realtor_no=r.realtor_no " +
            "group by r.realtor_no " +
            "order by review desc, starScore desc LIMIT 4", nativeQuery = true)
    List<RealtorProjectionInterface> findAllByOrderByCountByReviewsDesc();

    @Query(value = "SELECT r.name, r.image_src as imageSrc, r.corp, COUNT(v.review_no) as review, r.phone, " +
            "r.rating_score as starScore, count(i.realtor_no) as total, r.description, r.business_address as businessAddress FROM live.realtor r " +
            "left join review v on r.realtor_no=v.realtor_no " +
            "left join item i on i.realtor_no=r.realtor_no " +
            "group by r.realtor_no " +
            "order by starScore desc, review desc LIMIT 4", nativeQuery = true)
    List<RealtorProjectionInterface> findAllByOrderByCountByStarRatingDesc();

    @Query(value = "SELECT r.name, r.image_src as imageSrc, r.corp, COUNT(v.review_no) as review, r.phone, " +
            "r.rating_score as starScore, count(i.realtor_no) as total, r.description, r.business_address as businessAddress FROM live.realtor r " +
            "left join review v on r.realtor_no=v.realtor_no " +
            "left join item i on i.realtor_no=r.realtor_no " +
            "group by r.realtor_no " +
            "order by count(i.realtor_no) desc, starScore desc, review desc LIMIT 4", nativeQuery = true)
    List<RealtorProjectionInterface> findAllByOrderByCountByItemDesc();

    @Query(value = "SELECT r.* FROM realtor r "
        + "inner join item i "
        + "on r.realtor_no = i.realtor_no "
        + "inner join house h "
        + "on i.house_no = h.house_no "
        + "where h.region_code LIKE :regionCode% AND h.contracted = false "
        + "group by r.realtor_no order by r.rating_score DESC", nativeQuery=true)
    List<Realtor> findDistinctRealtor(String regionCode);

    @Query(value = "(SELECT i.item_no as itemNo, m.image_src as imageSrc, i.deposit, i.rent, h.address, h.floor, i.building_name as buildingName, h.exclusive_private_area as area FROM realtor r "
        + "inner join item i "
        + "on r.realtor_no = i.realtor_no "
        + "left join item_image m "
        + "on m.item_no = i.item_no "
        + "inner join house h "
        + "on i.house_no = h.house_no "
        + "where h.region_code LIKE :regionCode% and r.realtor_no=:realtorNo AND h.contracted = false "
        + "AND m.item_image_no in (select min(item_image_no) from item_image group by item_no)) "
        + "UNION DISTINCT "
        + "(SELECT i.item_no as itemNo, m.image_src as imageSrc, i.deposit, i.rent, h.address, h.floor, i.building_name as buildingName, h.exclusive_private_area as area FROM realtor r "
        + "inner join item i "
        + "on r.realtor_no = i.realtor_no "
        + "left join item_image m "
        + "on m.item_no = i.item_no "
        + "inner join house h "
        + "on i.house_no = h.house_no "
        + "where h.region_code NOT LIKE :regionCode% and r.realtor_no=:realtorNo AND h.contracted = false "
        + "AND m.item_image_no in (select min(item_image_no) from item_image group by item_no))", nativeQuery=true)
    List<RealtorByRegionProjectionInterface> findRealtorDetailByRegion(Long realtorNo, String regionCode);
}
