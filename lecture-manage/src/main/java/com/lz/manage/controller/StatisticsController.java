package com.lz.manage.controller;

import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.service.IStatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 统计
 *
 * @Project: Lecture
 * @Author: YY
 * @CreateTime: 2026-01-18  19:06
 * @Version: 1.0
 */
@RestController
@RequestMapping("/manage/statistics")
public class StatisticsController extends BaseController {
    @Resource
    private IStatisticsService statisticsService;

    /**
     * 评论数
     */
    @GetMapping("/evaluate")
    @PreAuthorize("@ss.hasPermi('manage:statistics')")
    public AjaxResult evaluateStatistics(StatisticsRequest request){
        return success(statisticsService.evaluateStatistics(request));
    }
}
