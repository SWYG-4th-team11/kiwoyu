package com.swyp.kiwoyu.email.service;

import com.swyp.kiwoyu.email.domain.EmailMessage;
import com.swyp.kiwoyu.global.util.RedisUtil;
import com.swyp.kiwoyu.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine templateEngine;

    private final UserService userService;

    private final RedisUtil redisUtil;

    // 이메일을 전송
    public String sendMail(EmailMessage emailMessage, String type) {
        String authNum = createCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        if (type.equals("password")) {
            userService.setTempPassword(emailMessage.getTo(), authNum);}
//        }else if (type.equals("email")) {
//            redisUtil.setDataExpire(emailMessage.getTo(), authNum, 3);
//        }
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(setContext(authNum, type), true);
            javaMailSender.send(mimeMessage);

            log.info("Send email successfully");

            return authNum;

        } catch (MessagingException e) {
            log.info("Failed to send mail");
            throw new RuntimeException(e);
        }
    }

    // 인증번호 및 임시 비밀번호 생성 메서드
    public String createCode() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0:
                    sb.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    sb.append((char) ((int) random.nextInt(26) + 65));
                    break;
                default:
                    sb.append(random.nextInt(9));
            }
        }
        return sb.toString();
    }

    // thymeleaf를 통한 html 적용
    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }

    // 인증코드 검증
    public boolean verifyEmailCode(String email, String code) {
        String savedCode = redisUtil.getData(email);
        if (code.equals(savedCode)) {
            redisUtil.deleteData(email);    //검증 후 인증코드 삭제
            return true;
        }
        return false;
    }
}