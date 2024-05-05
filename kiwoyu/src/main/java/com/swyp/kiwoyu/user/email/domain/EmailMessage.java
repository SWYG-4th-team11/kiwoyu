package com.swyp.kiwoyu.user.email.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailMessage {

    private String to;  //수신자
    private String subject;
    private String message;
}