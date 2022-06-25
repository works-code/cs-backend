package com.kakao.cs.repository;

import com.kakao.cs.vo.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByCustomerId(String customerId);
    Inquiry findBySeq(Long seq);
    List<Inquiry> findByAnswerSeqIsNull();
}
