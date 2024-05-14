package com.swyp.kiwoyu.user.service;
import com.swyp.kiwoyu.jwt.JwtProvider;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.repository.MandalartRepository;
import com.swyp.kiwoyu.user.domain.User;
import com.swyp.kiwoyu.user.dto.*;
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

    public Optional<User> getUserByNickname(String nickname) { return userRepository.findByNickname(nickname);}

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

        /* Encode password */
        String encodedPw = passwordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(encodedPw);

        return userRepository.save(user);
    }


    //로그인
    public User authenticate(LoginRequest loginRequest) {
        // 이메일로 사용자를 찾음
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            // 비밀번호 검증
            System.out.println(user.getPassword());
            System.out.println(loginRequest.getPassword());
            if (
                loginRequest.getPassword().contentEquals(user.getPassword())
                || passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())
                //TODO: remove raw comparison
            ) {
                // 비밀번호가 일치할시 해당 사용자 반환

                return user;
            }
        }
        // 인증 실패
        throw new IllegalArgumentException("Invalid credentials");
    }


    public void setTempPassword(String email, String tempPassword) {
        // 이메일을 기반으로 사용자를 찾아서 임시 비밀번호를 설정합니다.
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            // 사용자가 존재하는 경우에만 임시 비밀번호를 설정합니다.
            User user = userOptional.get();
            user.setPassword(tempPassword);
            // 사용자 업데이트
            userRepository.save(user);
        } else {
            // 사용자를 찾을 수 없는 경우에는 예외 처리를 수행합니다.
            throw new RuntimeException("User not found");
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
    // 마이페이지 정보 수정

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

            if (existingUser.getPassword().equals(updatePasswordRequestDto.getPassword())) {
                String encryptedPassword = passwordEncoder.encode(updatePasswordRequestDto.getNewPassword());
                existingUser.setPassword(encryptedPassword);
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
}