package com.github.jjjzzzqqq.qiangke.service.impl;


import com.github.jjjzzzqqq.qiangke.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("courseServiceMysql")
public class CourseServiceMysqlImpl extends CourseBaseService{

    @Resource
    private CourseMapper courseMapper;

    @Override
    public boolean snatch(Long courseId) {
        return courseMapper.updateSurplusNumber(courseId) != 0;
    }
}
