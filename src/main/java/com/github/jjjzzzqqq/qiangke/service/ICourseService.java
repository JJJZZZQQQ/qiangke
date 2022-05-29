package com.github.jjjzzzqqq.qiangke.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jjjzzzqqq.qiangke.entity.Course;

public interface ICourseService extends IService<Course> {

    Boolean snatchCourse(Long courseId, Long userId);

    boolean snatch(Long courseId);
}
