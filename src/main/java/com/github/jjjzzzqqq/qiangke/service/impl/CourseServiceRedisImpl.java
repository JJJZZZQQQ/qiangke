package com.github.jjjzzzqqq.qiangke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.jjjzzzqqq.qiangke.common.RedisKeyConstant;
import com.github.jjjzzzqqq.qiangke.entity.Course;
import com.github.jjjzzzqqq.qiangke.mapper.CourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 通过Redis incr decr命令实现滑动库存
 * 由于incr decr命令保证原子性
 * <br>
 * 所以此处对于每一个库存值，只有一个客户端会获取到
 * <br>
 * 但是需要注意极端网络情况，比如Redis主备切换导致的value回拨
 * <br>
 * 如果想对这种情况进行处理，那么我们可以针对每一个库存值都加对应的分布式锁
 * <br>
 * 来保证一定不会出现问题
 */
@Service("courseServiceRedis")
public class CourseServiceRedisImpl extends CourseBaseService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CourseServiceRedisImpl.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 库存值从0 - MaxNum,不需要进行库存预热的操作
     * <br>
     * 此处只扣减了Redis的库存,如果想扣减到MySQL中
     * <br>
     * 1.由定时任务扫描Redis更新库存到MySQL中(推荐,占有数据库资源更少)
     * <br>
     *     <br>
     * 2.使用MQ异步削峰扣减到MySQL中
     */
    @Override
    public boolean snatch(Long courseId) {
        //1.获取课程库存Key
        String redisKey = RedisKeyConstant.GET_SNATCH_COURSE(courseId);
        //2.通过incr命令扣减库存
        Long usedCount = redisTemplate.opsForValue().increment(redisKey);

        if (usedCount == null) {
            LOGGER.error("redis抢课incr失败, key : {}", redisKey);
            return false;
        }

        //3.超出库存判断，进行恢复库存
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        Course course = courseMapper.selectOne(queryWrapper.eq("course_id", courseId));
        if(course == null) {
            return false;
        }
        Integer totalCount = course.getNumber();
        if (usedCount > totalCount) {
            redisTemplate.opsForValue().decrement(redisKey);
            return false;
        }
        return true;
    }
}
