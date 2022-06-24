package com.kakao.cs.controller;

import com.kakao.cs.service.CommonService;
import com.kakao.cs.service.UserService;
import com.kakao.cs.vo.Entity.User;
import com.kakao.cs.vo.validation.UserVaildation;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin
@Slf4j
@Api(tags = {"03. User"})
@RequestMapping(path = "/user")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private CommonService commonService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody @Valid UserVaildation.User user, BindingResult result){
        if(commonService.isVaild(result)){
            return service.login(user.getUsername(), user.getPassword());
        }
        return false;
    }

    @RequestMapping(path = "/join", method = RequestMethod.POST)
    public boolean join(@RequestBody @Valid UserVaildation.User user, BindingResult result){
        if(commonService.isVaild(result)){
            return service.join(
                    User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .build());
        }
        return false;
    }
}
