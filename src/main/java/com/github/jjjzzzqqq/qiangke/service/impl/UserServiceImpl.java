package com.github.jjjzzzqqq.qiangke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jjjzzzqqq.qiangke.entity.User;
import com.github.jjjzzzqqq.qiangke.mapper.UserMapper;
import com.github.jjjzzzqqq.qiangke.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
