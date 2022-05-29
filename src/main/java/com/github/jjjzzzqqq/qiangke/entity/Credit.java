package com.github.jjjzzzqqq.qiangke.entity;

import lombok.Data;

/**
 * @author jjjzzzqqq.github.io
 * @since  2022/5/27  19:42
 */
@Data
public class Credit {
    private Long id;
    private Long userId;
    private Long courseId;
    private int score;
    private String source;
}
