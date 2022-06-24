package com.kakao.cs.vo.validation;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class InquiryValidation {

    @Data
    public static class CounselorSet{
        @NotNull(message = "inquirySeq is not null!")
        private Long inquirySeq;
        @NotEmpty(message = "counselorId is not empty!")
        private String counselorId;
    }

    @Data
    public static class InquiryRegister{
        @NotEmpty(message = "customerId is not empty!")
        private String customerId;
        @NotEmpty(message = "title is not empty!")
        private String title;
        @NotEmpty(message = "content is not empty!")
        private String content;
    }

}
