package com.kakao.cs.queue.producer;

import com.google.gson.Gson;
import com.kakao.cs.vo.Entity.Inquiry;
import com.kakao.cs.vo.validation.InquiryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class InquiryProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void pushInquiry(String name, InquiryValidation.InquiryRegister message){
        jmsTemplate.convertAndSend(name, new Gson().toJson(message));
    }

    public void pushInquiry(String name, Inquiry message){
        String a = new Gson().toJson(message);
        jmsTemplate.convertAndSend(name, new Gson().toJson(message));
    }

}
