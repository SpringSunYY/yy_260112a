package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.model.domain.Evaluate;
import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.service.IEvaluateService;
import com.lz.manage.service.IStatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 统计服务实现
 *
 * @Project: Lecture
 * @Author: YY
 * @CreateTime: 2026-01-18  19:08
 * @Version: 1.0
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Resource
    private IEvaluateService evaluateService;
    @Override
    public Long evaluateStatistics(StatisticsRequest request) {
        if(SecurityUtils.hasRole("teacher")&&!SecurityUtils.isAdmin(SecurityUtils.getUserId())){
            request.setTeacherId(SecurityUtils.getUserId());
        }
        LambdaQueryWrapper<Evaluate> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(request.getTeacherId())) {
            queryWrapper.eq(Evaluate::getTeacherId, request.getTeacherId());
        }
        if (StringUtils.isNotNull(request.getLectureId())) {
            queryWrapper.eq(Evaluate::getLectureId, request.getLectureId());
        }
        return evaluateService.count(queryWrapper);
    }
}
