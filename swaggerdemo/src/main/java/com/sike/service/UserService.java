package com.sike.service;

import com.sike.entity.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    /**
     * 模拟数据库数据，
     */
    private static List<User> list = Arrays.asList(
            new User("张三", "123"),
            new User("李四", "456"),
            new User("王五", "789")
    );

    public List<User> findAllUsers(){
        return list;
    }

    public User findUserByName(String username){
        for(User user:list){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
