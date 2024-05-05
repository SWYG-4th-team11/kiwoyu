package com.swyp.kiwoyu.user.controller;

import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.dto.LoginRequest;
import com.swyp.kiwoyu.user.dto.SignUpRequest;
import com.swyp.kiwoyu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        User createdUser = userService.signUp(signUpRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 사용자 인증
            User authenticatedUser = userService.authenticate(loginRequest);
            return ResponseEntity.ok(authenticatedUser);
        } catch (IllegalArgumentException e) {
            // 인증 실패
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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

//    @GetMapping("/info/{email}")
//    public ResponseEntity<User> getUserInfoByEmail(@PathVariable String email) {
//        // 이메일을 기반으로 사용자 정보를 가져옴
//        Optional<User> userOptional = userService.getUserByEmail(email);
//
//        if (userOptional.isPresent()) {
//            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
//        } else {
//            // 해당 이메일을 가진 사용자를 찾을 수 없는 경우
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}