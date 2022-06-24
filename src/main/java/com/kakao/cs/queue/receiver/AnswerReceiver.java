package com.kakao.cs.queue.receiver;

import com.google.gson.Gson;
import com.kakao.cs.service.AnswerService;
import com.kakao.cs.vo.AnswerInquiry;
import com.kakao.cs.vo.Entity.Answer;
import com.kakao.cs.vo.enums.QueueName;
import com.kakao.cs.vo.validation.AnswerValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AnswerReceiver {

    @Autowired
    private AnswerService answerService;

    @JmsListener(destination = "RA")
    public void pullRegisterAnswer(String message){
        AnswerInquiry answerInquiry = new Gson().fromJson(message, AnswerInquiry.class);
        answerService.setAnswer(
                answerInquiry.getInquiry(),
                answerInquiry.getAnswer());
    }

}
