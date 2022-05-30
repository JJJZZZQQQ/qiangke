package com.github.jjjzzzqqq.qiangke.service.impl;


import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.jjjzzzqqq.qiangke.entity.Course;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Lua脚本可以使多个Redis命令变成一段脚本
 * <br>
 * Redis 服务器会单线程原子性的执行 lua 脚本
 * <br>
 * 通过lua脚本,来解决抢课时的超卖问题,并且,也不会出现库存剩余的情况
 * <br>
 * 本质上就是将用户的抢课请求串行化执行,与incr decr命令异曲同工之妙
 */
@Service("courseServiceLua")
public class CourseServiceLuaImpl extends CourseBaseService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 1.判断当前已用库存是否大于等于总库存
     * 2.1如果大于等于，直接返回失败
     * 2.2如果小于，则调用incr命令增加已用库存,并返回成功
     * <br>
     * 通过lua脚本,将判断与扣减这两步操作变成一个分布式环境下的原子操作
     * <br>
     * 保证了并发安全
     */
    @Override
    public boolean snatch(Long courseId) {
        String script =
                "if (redis.call('get' ,KEYS[1]) >= tonumber(ARGV[1])) then " +
                "return false ; " +
                "end; " +
                "redis.call('incrby' , KEYS[1], 1); "+
                "return true; ";

        //构建Redis 脚本
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(script);
        redisScript.setResultType(Boolean.class);

        //keys
        List<String> keys = Collections.singletonList("course:" + courseId);

        //args
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        Course course = courseMapper.selectOne(queryWrapper.eq("course_id", courseId));
        if(course == null) {
            return false;
        }
        Integer totalCount = course.getNumber();
        List<String> args = Collections.singletonList(Long.toString(totalCount));

        //执行lua脚本
        Boolean result = redisTemplate.execute(redisScript, keys, args);
        return result != null && result ;
    }
}
