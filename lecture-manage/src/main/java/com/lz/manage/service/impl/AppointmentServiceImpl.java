package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.AppointmentMapper;
import com.lz.manage.model.domain.Appointment;
import com.lz.manage.service.IAppointmentService;
import com.lz.manage.model.dto.appointment.AppointmentQuery;
import com.lz.manage.model.vo.appointment.AppointmentVo;

/**
 * 预约信息Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService
{

    @Resource
    private AppointmentMapper appointmentMapper;

    //region mybatis代码
    /**
     * 查询预约信息
     *
     * @param id 预约信息主键
     * @return 预约信息
     */
    @Override
    public Appointment selectAppointmentById(Long id)
    {
        return appointmentMapper.selectAppointmentById(id);
    }

    /**
     * 查询预约信息列表
     *
     * @param appointment 预约信息
     * @return 预约信息
     */
    @Override
    public List<Appointment> selectAppointmentList(Appointment appointment)
    {
        return appointmentMapper.selectAppointmentList(appointment);
    }

    /**
     * 新增预约信息
     *
     * @param appointment 预约信息
     * @return 结果
     */
    @Override
    public int insertAppointment(Appointment appointment)
    {
        appointment.setCreateTime(DateUtils.getNowDate());
        return appointmentMapper.insertAppointment(appointment);
    }

    /**
     * 修改预约信息
     *
     * @param appointment 预约信息
     * @return 结果
     */
    @Override
    public int updateAppointment(Appointment appointment)
    {
        appointment.setUpdateTime(DateUtils.getNowDate());
        return appointmentMapper.updateAppointment(appointment);
    }

    /**
     * 批量删除预约信息
     *
     * @param ids 需要删除的预约信息主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentByIds(Long[] ids)
    {
        return appointmentMapper.deleteAppointmentByIds(ids);
    }

    /**
     * 删除预约信息信息
     *
     * @param id 预约信息主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentById(Long id)
    {
        return appointmentMapper.deleteAppointmentById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Appointment> getQueryWrapper(AppointmentQuery appointmentQuery){
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = appointmentQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = appointmentQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long classroomId = appointmentQuery.getClassroomId();
        queryWrapper.eq( StringUtils.isNotNull(classroomId),"classroom_id",classroomId);

        Long lectureId = appointmentQuery.getLectureId();
        queryWrapper.eq( StringUtils.isNotNull(lectureId),"lecture_id",lectureId);

        Long teacherId = appointmentQuery.getTeacherId();
        queryWrapper.eq( StringUtils.isNotNull(teacherId),"teacher_id",teacherId);

        String status = appointmentQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Long auditUserId = appointmentQuery.getAuditUserId();
        queryWrapper.eq( StringUtils.isNotNull(auditUserId),"audit_user_id",auditUserId);

        Long userId = appointmentQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = appointmentQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<AppointmentVo> convertVoList(List<Appointment> appointmentList) {
        if (StringUtils.isEmpty(appointmentList)) {
            return Collections.emptyList();
        }
        return appointmentList.stream().map(AppointmentVo::objToVo).collect(Collectors.toList());
    }

}
