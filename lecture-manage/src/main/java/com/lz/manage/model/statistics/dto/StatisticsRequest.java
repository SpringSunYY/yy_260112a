package com.lz.manage.model.statistics.dto;

import lombok.Data;

/**
 * 统计
 *
 * @Project: Lecture
 * @Author: YY
 * @CreateTime: 2026-01-18  19:27
 * @Version: 1.0
 */
@Data
public class StatisticsRequest {
    /**
     * 讲师
     */
    private Long teacherId;

    /**
     * 讲座
     */
    private Long lectureId;
}
