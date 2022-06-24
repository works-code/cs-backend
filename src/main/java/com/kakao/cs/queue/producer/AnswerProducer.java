package com.kakao.cs.queue.producer;

import com.google.gson.Gson;
import com.kakao.cs.vo.AnswerInquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnswerProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void pushAnswer(String name, AnswerInquiry message){
        jmsTemplate.convertAndSend(name, new Gson().toJson(message));
    }
}
