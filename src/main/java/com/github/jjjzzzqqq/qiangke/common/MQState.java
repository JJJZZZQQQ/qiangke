package com.github.jjjzzzqqq.qiangke.common;

/**
 * @author jjjzzzqqq.github.io
 * @since  2022/5/28  19:42
 */
public enum MQState {

    WAIT("待发送", 0),
    SUCCESS("发送成功", 1),
    FAIL("发送失败", 2);


    String desc;

    Integer code;

    MQState(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
