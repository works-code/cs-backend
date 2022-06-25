package com.kakao.cs.queue.receiver;

import com.google.gson.Gson;
import com.kakao.cs.service.InquiryService;
import com.kakao.cs.vo.entity.Inquiry;
import com.kakao.cs.vo.validation.InquiryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class InquiryReceiver {

    @Autowired
    private InquiryService inquiryService;

    @JmsListener(destination = "RI")
    public void pullRegisterInquiry(String message){
        InquiryValidation.InquiryRegister register = new Gson().fromJson(message, InquiryValidation.InquiryRegister.class);
        inquiryService.setInquiry(
                Inquiry.builder()
                        .title(register.getTitle())
                        .content(register.getContent())
                        .customerId(register.getCustomerId())
                        .build());
    }

    @JmsListener(destination = "SIC")
    public void pullSetInquiryCounselor(String message){
        Inquiry inquiry = new Gson().fromJson(message, Inquiry.class);
        inquiryService.setCounselorId(inquiry);
    }
}
