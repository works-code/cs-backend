package com.kakao.cs.vo.validation;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnswerValidation {

    @Data
    public static class AnswerRegister{
        @NotNull(message = "inquirySeq is not null!")
        private Long inquirySeq;
        @NotEmpty(message = "counselorId is not empty!")
        private String counselorId;
        @NotEmpty(message = "counselorName is not empty!")
        private String counselorName;
        @NotEmpty(message = "content is not empty!")
        private String content;
    }
}
