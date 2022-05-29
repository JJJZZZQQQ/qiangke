package com.github.jjjzzzqqq.qiangke.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jjjzzzqqq.qiangke.entity.Course;
import com.github.jjjzzzqqq.qiangke.entity.Enroll;
import com.github.jjjzzzqqq.qiangke.mapper.CourseMapper;
import com.github.jjjzzzqqq.qiangke.mapper.EnrollMapper;
import com.github.jjjzzzqqq.qiangke.service.ICourseService;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author jjjzzzqqq.github.io
 * @since  2022/5/28  19:42
 * <br>
 * 使用模板方法设计模式，让抢课服务延迟到子类执行，便于后期维护拓展
 */
public abstract class CourseBaseService extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Resource
    protected CourseMapper courseMapper;

    @Resource
    private EnrollMapper enrollMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public Boolean snatchCourse(Long courseId, Long userId) {
        //编程式事务防止自调用导致事务失效
        return transactionTemplate.execute(status -> {
            //1.调用抢课服务
            boolean snatch = this.snatch(courseId);

            //2.成功后插入报名记录，否则返回失败
            if(!snatch) {
                return false;
            }
            Enroll enroll = new Enroll();
            enroll.setCourseId(courseId);
            enroll.setUserId(userId);
            enrollMapper.insert(enroll);
            return true;
        });
    }
}
