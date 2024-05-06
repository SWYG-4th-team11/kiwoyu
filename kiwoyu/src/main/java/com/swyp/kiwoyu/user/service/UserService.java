package com.swyp.kiwoyu.user.service;
import com.swyp.kiwoyu.jwt.JwtProvider;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.dto.LoginRequest;
import com.swyp.kiwoyu.user.dto.LoginResponse;
import com.swyp.kiwoyu.user.dto.SignUpRequest;
import com.swyp.kiwoyu.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createOrUpdateUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) { return userRepository.findByEmail(email);}

    // 회원가입
    public User signUp(SignUpRequest signUpRequest) {

        // 닉네임 중복 체크
        if (userRepository.existsByNickname(signUpRequest.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = new User();
        user.setNickname(signUpRequest.getNickname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());

        return userRepository.save(user);
    }

    //로그인
    public User authenticate(LoginRequest loginRequest) {
        // 이메일로 사용자를 찾음
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            // 비밀번호 검증
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return user;
            }
        }
        // 인증 실패
        throw new IllegalArgumentException("Invalid credentials");
    }

    public LoginResponse generateLoginResponse(User user){

        String token = JwtProvider.getInstance().createToken(user.getEmail());
        System.out.println("login success token: "+token);
        return new LoginResponse(user.getId(),user.getEmail(),user.getNickname(),token);
    }
    // 마이페이지 정보 수정
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setNickname(updatedUser.getNickname());
            existingUser.setPassword(updatedUser.getPassword());

            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}