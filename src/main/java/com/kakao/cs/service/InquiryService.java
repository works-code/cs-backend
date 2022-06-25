package com.kakao.cs.service;

import com.kakao.cs.queue.producer.InquiryProducer;
import com.kakao.cs.repository.InquiryRepository;
import com.kakao.cs.vo.entity.Inquiry;
import com.kakao.cs.vo.enums.QueueName;
import com.kakao.cs.vo.validation.InquiryValidation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private InquiryProducer producer;

    public List<Inquiry> getInquirysByCostomerId(String customerId){
        if(StringUtils.isEmpty(customerId)){
            return new ArrayList<>();
        }

        List<Inquiry> results = inquiryRepository.findByCustomerId(customerId);
        return CollectionUtils.isEmpty(results) ? new ArrayList<>() : results;
    }

    @Cacheable(cacheNames = "inquirysNotExistAnswer")
    public List<Inquiry> getInquirysNotExistAnswer(){
        List<Inquiry> results = inquiryRepository.findByAnswerSeqIsNull();
        return CollectionUtils.isEmpty(results) ? new ArrayList<>() : results;
    }

    public void setInquiryByQueue(InquiryValidation.InquiryRegister inquiry){
        producer.pushInquiry(QueueName.RegisterInquiry.getName(), inquiry);
    }

    @CacheEvict(cacheNames = "inquirysNotExistAnswer", allEntries = true)
    public void setInquiry(Inquiry inquiry){
        inquiryRepository.save(inquiry);
    }

    public boolean checkAndInquiryCounselor(InquiryValidation.CounselorSet counselor){
        Inquiry inquiry = inquiryRepository.findBySeq(counselor.getInquirySeq());
        if(!ObjectUtils.isEmpty(inquiry) && StringUtils.isEmpty(inquiry.getCounselorId())){
            inquiry.setCounselorId(counselor.getCounselorId());
            producer.pushInquiry(QueueName.SetInquiryCounselor.getName(), inquiry);
            return true;
        }
        return false;
    }

    @CacheEvict(cacheNames = "inquirysNotExistAnswer", allEntries = true)
    public void setCounselorId(Inquiry inquiry){
        inquiryRepository.save(inquiry);
    }
}
