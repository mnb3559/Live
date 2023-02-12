package com.ssafy.live.account.realtor.controller;

import com.ssafy.live.account.common.error.ErrorHandler;
import com.ssafy.live.account.realtor.controller.dto.RealtorRequest;
import com.ssafy.live.account.realtor.service.RealtorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/realtors")
public class RealtorController {

    @Value("${com.example.upload.path}")
    private String uploadPath;
    private final RealtorService realtorService;

    @PostMapping
    public ResponseEntity<?> signUp(@Validated @RequestPart(value = "SignUp") RealtorRequest.SignUp signUp, Errors errors, @RequestPart(value = "file", required = false) MultipartFile uploadFile) throws IOException {
        // validation check
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorHandler.refineErrors(errors));
        }
        return realtorService.signUp(signUp, uploadFile);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody RealtorRequest.Login login, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorHandler.refineErrors(errors));
        }
        log.info("공인중개사 로그인");
        return realtorService.login(login);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated @RequestBody RealtorRequest.Reissue reissue, Errors errors) {
        System.out.println("r "+reissue.getAccessToken());
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorHandler.refineErrors(errors));
        }
        return realtorService.reissue(reissue);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        return realtorService.logout(authentication);
    }

    @DeleteMapping
    public ResponseEntity<?> withdrawl(Authentication authentication) {
        log.info("회원 탈퇴");
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return realtorService.withdrawl(principal);
    }

    @GetMapping("/authority")
    public ResponseEntity<?> authority() {
        log.info("ADD ROLE_REALTOR");
        return realtorService.authority();
    }

    @PostMapping("/info")
    public ResponseEntity<?> updateRealtor(Authentication authentication, @RequestPart(value = "Update") RealtorRequest.Update request, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        log.info("공인중개사 정보수정");
        return realtorService.updateRealtor(principal, request, file);
    }

    @PostMapping("/passcheck")
    public ResponseEntity<?> temporaryPassword(@RequestBody RealtorRequest.FindPassword request) {
        log.info("공인중개사 임시비밀번호 발급");
        return realtorService.temporaryPassword(request);
    }

    @GetMapping
    public ResponseEntity<?> findRealtorDetail(Authentication authentication) {
        log.info("공인중개사 정보 상세 조회");
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return realtorService.findRealtorDetail(principal);
    }

    @GetMapping ("/{realtorNo}/consultings")
    public ResponseEntity<?> findRealtorDetailByRegion(@PathVariable("realtorNo") Long realtorNo, @RequestParam("regionCode") String regionCode) {
        log.info("예약페이지 - 공인중개사 정보 상세 조회");
        return realtorService.findRealtorDetailByRegion(realtorNo, regionCode);
    }

    @GetMapping("/region")
    public ResponseEntity<?> findRealtorByRegion(@RequestParam("regionCode") String regionCode) {
        log.info("특정 지역 공인중개사 목록 조회");
        return realtorService.findDistinctRealtorWithItemsByHouseByRegion(regionCode);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> findRealtorList(Authentication authentication, @RequestParam String orderBy) {
        log.info("메인페이지 공인중개사 목록 조회");
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return realtorService.findRealtorList(principal, orderBy);
    }
}
