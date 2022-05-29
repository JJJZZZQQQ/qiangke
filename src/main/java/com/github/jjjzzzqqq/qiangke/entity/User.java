package com.github.jjjzzzqqq.qiangke.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author jjjzzzqqq.github.io
 * @since  2022/5/27  19:42
 */
@Data
public class User {
    private Long userId;
    private String userName;
    private String nickName;
    private Date createTime;
    private Date updateTime;
}
