package com.github.jjjzzzqqq.qiangke.service.impl;


import org.springframework.stereotype.Service;

@Service("courseServiceLua")
public class CourseServiceLuaImpl extends CourseBaseService{
    @Override
    public boolean snatch(Long courseId) {
        return true;
    }
}
