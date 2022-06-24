package com.kakao.cs.service;

import com.kakao.cs.repository.UserRepository;
import com.kakao.cs.vo.Entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean login(String username, String password){
        User user = userRepository.findByUsername(username);
        if(!ObjectUtils.isEmpty(user) && BCrypt.checkpw(password,user.getPassword())){
            return true;
        }
        return false;
    }

    public boolean join(User user){
        User check = userRepository.findByUsername(user.getUsername());
        if(ObjectUtils.isEmpty(check)){
            user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
