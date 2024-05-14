package com.swyp.kiwoyu.user.controller;

import com.swyp.kiwoyu.global.common.ResponseDto;
import com.swyp.kiwoyu.goal.dto.UpdateGoalResponseDto;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.dto.*;
import com.swyp.kiwoyu.user.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
//        Optional<User> user = userService.getUserById(id);
//        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<MyPageInfoDto> getMyPageInfoById(@PathVariable("id") Long id) {
        MyPageInfoDto dto = userService.getMyPageInfo(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        User createdUser = userService.signUp(signUpRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 사용자 인증
            User authenticatedUser = userService.authenticate(loginRequest);
            LoginResponse lr = userService.generateLoginResponse(authenticatedUser);
            return ResponseEntity.ok(new LoginResponseDto("ok",lr, ""));
        } catch (IllegalArgumentException e) {
            // 인증 실패
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity.ok(new LoginResponseDto("fail",null, "로그인 실패"));

        }
    }

    // 닉네임 중복체크
    @PostMapping("/nickname/check-unique")
    public ResponseEntity<CheckNicknameDto> checkUniqueNickname(@RequestBody CheckUniqueNicknameRequestDto dto) {
        try {
            // 사용자 인증
            Boolean exists = userService.checkUniqueNickname(dto);
            return ResponseEntity.ok(new CheckNicknameDto("ok",!exists,""));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(new CheckNicknameDto("fail",null, "중복체크 실패"));
        }
    }

    // 닉네임 변경
    @PutMapping("/nickname")
    public ResponseEntity<User> putUserNickName(@RequestBody PutUserNickNameRequestDto dto) {
        try {
            // 사용자 인증
            User user = userService.updateUniqueNickname(dto);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }

    // 마이페이지 정보 수정
    @PutMapping("/mypage/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) { //사용자 존재할시
            user.setId(id);
            User updatedUser = userService.updateUser(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  //그런회원은없음
        }
    }

    //유저 삭제 (탈퇴)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //비밀번호 변경
    @PostMapping("/update-password")
    public ResponseEntity<User> updatePassword(@RequestBody UpdatePasswordRequestDto updatePasswordRequestDto){
        try {
            String encryptedNewPassword = passwordEncoder.encode(updatePasswordRequestDto.getNewPassword());
            updatePasswordRequestDto.setNewPassword(encryptedNewPassword);

            User updatedUser = userService.updatePassword(updatePasswordRequestDto);

            return ResponseEntity.ok(updatedUser);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
