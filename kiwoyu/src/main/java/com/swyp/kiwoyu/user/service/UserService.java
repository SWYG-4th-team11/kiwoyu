package com.swyp.kiwoyu.user.service;
import com.swyp.kiwoyu.jwt.JwtProvider;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepository;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.dto.*;
import com.swyp.kiwoyu.user.exception.UserNotFoundException;
import com.swyp.kiwoyu.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MandalartRepository mandalartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 회원가입
    public SignUpResponseDto signUp(SignUpRequest signUpRequest) {
        try{
            // 닉네임 중복 체크
            if (userRepository.existsByNickname(signUpRequest.getNickname())) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            }

            // 이메일 중복 체크
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }

            User user = new User();
            user.setNickname(signUpRequest.getNickname());
            user.setEmail(signUpRequest.getEmail());

            /* Encode password */
            String encodedPw = passwordEncoder.encode(signUpRequest.getPassword());
            user.setPassword(encodedPw);

            User createdUser = userRepository.save(user);
            return new SignUpResponseDto("ok",new SignUpRequest(createdUser),"");
        } catch(Exception e){
            System.out.println(e);
            return new SignUpResponseDto("fail",null,e.getMessage());
        }
    }


    //로그인
    public User authenticate(LoginRequest loginRequest) {

        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new IllegalArgumentException("Invalid credentials");
    }


    public void setTempPassword(String email, String encryptedTempPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(encryptedTempPassword);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found" + email);
        }
    }


    // 마이페이지 정보 수정(닉네임, 비밀번호 변경 가능)
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) { //사용자 존재 할 시
            User existingUser = existingUserOptional.get();
            existingUser.setNickname(updatedUser.getNickname());
            existingUser.setPassword(updatedUser.getPassword());
            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public LoginResponse generateLoginResponse(User user){

        String token = JwtProvider.getInstance().createToken(user.getEmail());
        System.out.println("login success token: "+token);
        return new LoginResponse(user,token);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Boolean checkUniqueNickname(CheckUniqueNicknameRequestDto dto){
        Boolean res = true;
        String nickname = dto.getNickName();
        res = userRepository.existsByNickname(nickname);
        return res;
    }
    public User updateUniqueNickname(PutUserNickNameRequestDto dto){
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        user.setNickname(dto.getNickName());
        userRepository.save(user);
        user = userRepository.findById(dto.getUserId()).orElse(null);
        return user;
    }

    //비밀번호 변경
    public User updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto){
        Optional<User> existingUserOptional = userRepository.findById(updatePasswordRequestDto.getId());

        if ((existingUserOptional.isPresent())) {
            User existingUser = existingUserOptional.get();

            if (passwordEncoder.matches(updatePasswordRequestDto.getPassword(), existingUser.getPassword())) {
                existingUser.setPassword(updatePasswordRequestDto.getNewPassword());
                return userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("Password doesn't match");
            }
        }else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public MyPageInfoDto getMyPageInfo(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return new MyPageInfoDto();
        }
        List<Mandalart> ms= mandalartRepository.findByUserId(id);
        MyPageInfoDto res = new MyPageInfoDto(user);

        if (ms.size()< 1){
            return new MyPageInfoDto();
        } else {
            return new MyPageInfoDto(user, ms.get(0));
        }

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
}