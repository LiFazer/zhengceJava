package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.entity.UserDO;
import cn.gov.hebei.ylbzj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl{
    @Autowired
    private UserService userService;

    public UserDetails loadUserByUsername(String username,String password) throws UsernameNotFoundException {

        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        //根据用户名查询用户
        UserDO userDO = userService.selectByName(username);
        if (userDO == null) {
            throw new RuntimeException("用户不存在");
        }
        return userDO;
    }
}
