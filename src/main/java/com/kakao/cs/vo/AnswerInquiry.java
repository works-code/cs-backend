package com.kakao.cs.vo;

import com.kakao.cs.vo.Entity.Answer;
import com.kakao.cs.vo.Entity.Inquiry;
import lombok.Data;

@Data
public class AnswerInquiry {
    private Answer answer;
    private Inquiry inquiry;
}
