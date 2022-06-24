package com.kakao.cs.vo.validation;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class UserVaildation {

    @Data
    public static class User{
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
    }

}
