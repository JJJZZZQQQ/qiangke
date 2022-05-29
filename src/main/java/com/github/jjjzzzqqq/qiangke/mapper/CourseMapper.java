package com.github.jjjzzzqqq.qiangke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jjjzzzqqq.qiangke.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface CourseMapper extends BaseMapper<Course> {

    Integer updateMqState(@Param("courseId") Long courseId, @Param("mqState") Integer mqState);

    List<Long> getCourseIdListByMqFail(@Param("beginId") Long beginId , @Param("size") Integer size);

    Long getMaxId();

    Integer updateSurplusNumber(@Param("courseId")Long courseId);
}