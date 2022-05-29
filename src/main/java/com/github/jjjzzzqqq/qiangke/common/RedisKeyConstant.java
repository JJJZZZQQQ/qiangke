package com.github.jjjzzzqqq.qiangke.common;

public class RedisKeyConstant {


    public static String GET_SNATCH_COURSE(Long courseId) {
        return "course:" + courseId;
    }

    public static String GET_SNATCH_LOCK(Long courseId, Long num) {
        return "course:" + courseId + ":" +  num;
    }

}
