package com.kakao.cs.controller;

import com.kakao.cs.service.AnswerService;
import com.kakao.cs.service.CommonService;
import com.kakao.cs.vo.Entity.Answer;
import com.kakao.cs.vo.validation.AnswerValidation;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin
@Slf4j
@Api(tags = {"01. Answer"})
@RequestMapping(path = "/answer")
@RestController
public class AnswerController {

    @Autowired
    private AnswerService service;

    @Autowired
    private CommonService commonService;

    @Operation(summary = "답변 등록", description = "문의글에 답변을 등록합니다.")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public boolean answerRegister(@RequestBody @Valid AnswerValidation.AnswerRegister answer, BindingResult result){
        if(commonService.isVaild(result)){
            return service.checkAndSetAnswerByQueue(answer);
        }
        return false;
    }

    @Operation(summary = "답변 조회", description = "문의글의 답변을 조회합니다.")
    @RequestMapping(path = "/get/{seq}", method = RequestMethod.GET)
    public Answer getAnswer(@PathVariable Long seq){
        return service.getAnswersBySeq(seq);
    }
}
