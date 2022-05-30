package com.github.jjjzzzqqq.qiangke;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.jjjzzzqqq.qiangke.entity.Course;
import com.github.jjjzzzqqq.qiangke.mapper.CourseMapper;
import com.github.jjjzzzqqq.qiangke.service.ICourseService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class QiangkeApplicationTests {

    @Resource
    private CourseMapper courseMapper;

    @Resource(name = "courseServiceLua")
    private ICourseService courseService;


    @Test
    void contextLoads() {
    }

    @Test
    void updateNumberTest() {
        System.out.println(courseMapper.updateSurplusNumber(1L));
    }

    @Test
    void courseServiceTestByMysql() {
        System.out.println(courseService.snatchCourse(1L, 1L));
    }

    @Test
    void luaScriptTest() {
        System.out.println(courseService.snatchCourse(1L, 1L));
    }
    @Test
    void courseMapperSelectIdTest() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        Course course = courseMapper.selectOne(queryWrapper.eq("course_id", 1));
        System.out.println(course);
    }
}
