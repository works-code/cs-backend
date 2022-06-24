package com.kakao.cs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Slf4j
@Service
public class CommonService {

    public boolean isVaild(BindingResult result){
        if(result.hasFieldErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList){
                log.error("VALID ERROR :: {}", error.getDefaultMessage());
            }
            return false;
        }
        return true;
    }

}
