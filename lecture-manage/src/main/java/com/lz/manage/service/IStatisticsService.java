package com.lz.manage.service;

import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.statistics.vo.PieStatisticsVo;

import java.util.List;

public interface IStatisticsService {
    Long evaluateStatistics(StatisticsRequest request);

    List<PieStatisticsVo> signStatistics(StatisticsRequest request);
}
