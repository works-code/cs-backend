package com.kakao.cs.vo;

import com.kakao.cs.vo.entity.Answer;
import com.kakao.cs.vo.entity.Inquiry;
import lombok.Data;

@Data
public class AnswerInquiry {
    private Answer answer;
    private Inquiry inquiry;
}
