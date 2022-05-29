package com.github.jjjzzzqqq.qiangke.service.impl;

import com.github.jjjzzzqqq.qiangke.common.RedisKeyConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * 通过对每一个库存加分布式锁来保证不会超卖
 * 显然相比incr decr命令来说比较臃肿
 * <br>
 * 并且此会有库存剩余的问题,会出现大量用户抢课失败的情况,故不推荐
 * <br>
 * 当然,我们也可以通过加大锁的粒度,直接对整个课程加锁,并且使用Redisson这种阻塞的锁来加分布式锁,就可以避免库存剩余的问题
 * <br>
 * 但是程序整体性能会比较低
 */
@Service("courseServiceLock")
public class CourseServiceLockImpl extends CourseBaseService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean snatch(Long courseId) {

        //1. 从Redis查询当前已用库存
        String redisKey = RedisKeyConstant.GET_SNATCH_COURSE(courseId);
        Object o = redisTemplate.opsForValue().get(redisKey);
        Long usedCount = o == null ? 0L : (long) o;

        //2. 对当前库存量进行加锁
        String lockKey = RedisKeyConstant.GET_SNATCH_LOCK(courseId, usedCount);
        redisTemplate.opsForValue().setIfAbsent(lockKey, 1, 2, TimeUnit.SECONDS);


        //3. 判断已用库存是否小于总库存
        Integer totalCount = courseMapper.selectById(courseId).getNumber();
        if (usedCount >= totalCount) {
            redisTemplate.delete(lockKey);
            return false;
        }

        //4.扣减库存
        redisTemplate.opsForValue().increment(redisKey);
        //5.解锁

        redisTemplate.delete(lockKey);
        return true;
    }
}
