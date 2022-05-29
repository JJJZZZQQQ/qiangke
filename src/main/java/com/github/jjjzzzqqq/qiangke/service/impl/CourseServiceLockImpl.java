package com.github.jjjzzzqqq.qiangke.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("courseServiceLock")
public class CourseServiceLockImpl extends CourseBaseService{
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean snatch(Long courseId) {

        return true;
    }
}
