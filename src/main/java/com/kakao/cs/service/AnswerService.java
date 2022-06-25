package com.kakao.cs.service;

import com.kakao.cs.queue.producer.AnswerProducer;
import com.kakao.cs.repository.AnswerRepository;
import com.kakao.cs.repository.InquiryRepository;
import com.kakao.cs.vo.AnswerInquiry;
import com.kakao.cs.vo.entity.Answer;
import com.kakao.cs.vo.entity.Inquiry;
import com.kakao.cs.vo.enums.QueueName;
import com.kakao.cs.vo.validation.AnswerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private AnswerProducer answerProducer;

    public boolean checkAndSetAnswerByQueue(AnswerValidation.AnswerRegister answer){
        Inquiry inquiry = inquiryRepository.findBySeq(answer.getInquirySeq());
        if(!ObjectUtils.isEmpty(inquiry.getAnswerSeq())){
            return false;
        }
        AnswerInquiry answerInquiry = new AnswerInquiry();
        answerInquiry.setAnswer(
                Answer.builder()
                        .content(answer.getContent())
                        .counselorName(answer.getCounselorName())
                        .counselorId(answer.getCounselorId())
                        .build());
        answerInquiry.setInquiry(inquiry);
        answerProducer.pushAnswer(QueueName.RegisterAnswer.getName(), answerInquiry);
        return true;
    }

    @CacheEvict(cacheNames = "inquirysNotExistAnswer", allEntries = true)
    public void setAnswer(Inquiry inquiry, Answer answer){
        Answer result = answerRepository.save(answer);
        inquiry.setAnswerSeq(result.getSeq());
        inquiry.setCounselorId(result.getCounselorId());
        inquiryRepository.save(inquiry);
    }

    public Answer getAnswersBySeq(Long seq){
        return answerRepository.findById(seq).get();
    }
}
