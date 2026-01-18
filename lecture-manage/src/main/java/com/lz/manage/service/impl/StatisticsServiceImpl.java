package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.model.domain.Appointment;
import com.lz.manage.model.domain.Evaluate;
import com.lz.manage.model.domain.Sign;
import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.statistics.vo.PieStatisticsVo;
import com.lz.manage.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private ILectureService lectureService;

    @Resource
    private ISignService signService;

    @Resource
    private IAppointmentService appointmentService;

    @Override
    public Long evaluateStatistics(StatisticsRequest request) {
        if (SecurityUtils.hasRole("teacher") && !SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
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

    @Override
    public List<PieStatisticsVo> signStatistics(StatisticsRequest request) {
        if (SecurityUtils.hasRole("teacher") && !SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            request.setTeacherId(SecurityUtils.getUserId());
        }

        LambdaQueryWrapper<Sign> signWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Appointment> appointmentWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(request.getTeacherId())) {
            signWrapper.eq(Sign::getTeacherId, request.getTeacherId());
            appointmentWrapper.eq(Appointment::getTeacherId, request.getTeacherId());
        }
        if (StringUtils.isNotNull(request.getLectureId())) {
            signWrapper.eq(Sign::getId, request.getLectureId());
            appointmentWrapper.eq(Appointment::getLectureId, request.getLectureId());
        }
        Long signCount = signService.count(signWrapper);
        Long appointmentCount = appointmentService.count(appointmentWrapper);
        //签到人数
        PieStatisticsVo pieStatisticsVo = new PieStatisticsVo();
        pieStatisticsVo.setName("签到人数");
        pieStatisticsVo.setValue(signCount);
        //未签到人数
        PieStatisticsVo pieStatisticsVo1 = new PieStatisticsVo();
        pieStatisticsVo1.setName("未签到人数");
        pieStatisticsVo1.setValue(appointmentCount - signCount);
        ArrayList<PieStatisticsVo> pieStatisticsVos = new ArrayList<>();
        pieStatisticsVos.add(pieStatisticsVo);
        pieStatisticsVos.add(pieStatisticsVo1);
        return pieStatisticsVos;
    }
}
