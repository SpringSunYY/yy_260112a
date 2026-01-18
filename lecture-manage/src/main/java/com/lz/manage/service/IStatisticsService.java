package com.lz.manage.service;

import com.lz.manage.model.statistics.dto.StatisticsRequest;

public interface IStatisticsService {
    Long evaluateStatistics(StatisticsRequest request);
}
