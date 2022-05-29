package com.github.jjjzzzqqq.qiangke;

import com.github.jjjzzzqqq.qiangke.mapper.CourseMapper;
import com.github.jjjzzzqqq.qiangke.service.ICourseService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class QiangkeApplicationTests {

    @Resource
    private CourseMapper courseMapper;

    @Resource(name = "courseServiceMysql")
    private ICourseService courseService;

    @Test
    void contextLoads() {
    }

    @Test
    void updateNumberTest(){
        System.out.println(courseMapper.updateSurplusNumber(1L));
    }

    @Test
    void courseServiceTestByMysql(){
        System.out.println(courseService.snatchCourse(1L, 1L));
    }

}
