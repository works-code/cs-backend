package com.kakao.cs.controller;

import com.kakao.cs.service.CommonService;
import com.kakao.cs.service.InquiryService;
import com.kakao.cs.vo.entity.Inquiry;
import com.kakao.cs.vo.validation.InquiryValidation;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Slf4j
@Api(tags = {"00. Inquiry"})
@RequestMapping(path = "/inquiry")
@RestController
public class InquiryController {

    @Autowired
    private InquiryService service;

    @Autowired
    private CommonService commonService;

    @Operation(summary = "문의글 등록", description = "문의글을 등록 합니다.")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public boolean inquiryRegister(@RequestBody @Valid InquiryValidation.InquiryRegister inquiry, BindingResult result){
        if(commonService.isVaild(result)){
            service.setInquiryByQueue(inquiry);
            return true;
        }
        return false;
    }

    @Operation(summary = "문의글 상담사 지정", description = "문의글에 상담사를 지정 합니다.")
    @RequestMapping(path = "/set/counselor", method = RequestMethod.POST)
    public boolean setCounselor(@RequestBody @Valid InquiryValidation.CounselorSet counselor, BindingResult result){
        if(commonService.isVaild(result)){
            return service.checkAndInquiryCounselor(counselor);
        }
        return false;
    }

    @Operation(summary = "미 답변 문의글 조회", description = "답변이 없는 문의글을 조회 합니다.")
    @RequestMapping(path = "/get/noAnswer", method = RequestMethod.GET)
    public List<Inquiry> getInquiryNoAnswer(){
        return service.getInquirysNotExistAnswer();
    }

    @Operation(summary = "문의글 조회", description = "고객 ID로 문의글을 조회 합니다.")
    @RequestMapping(path = "/get/{customerId}", method = RequestMethod.GET)
    public List<Inquiry> getInquiry(@PathVariable String customerId){
        return service.getInquirysByCostomerId(customerId);
    }

}
