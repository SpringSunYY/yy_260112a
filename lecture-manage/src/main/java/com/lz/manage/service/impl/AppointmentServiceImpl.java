package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.AppointmentMapper;
import com.lz.manage.model.domain.Appointment;
import com.lz.manage.model.domain.Classroom;
import com.lz.manage.model.domain.Lecture;
import com.lz.manage.model.dto.appointment.AppointmentQuery;
import com.lz.manage.model.enums.AppointmentStatusEnum;
import com.lz.manage.model.enums.LectureStatusEnum;
import com.lz.manage.model.vo.appointment.AppointmentVo;
import com.lz.manage.service.IAppointmentService;
import com.lz.manage.service.IClassroomService;
import com.lz.manage.service.ILectureService;
import com.lz.system.service.ISysUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预约信息Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {

    @Resource
    private AppointmentMapper appointmentMapper;

    @Resource
    @Lazy
    private ILectureService lectureService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IClassroomService classroomService;


    //region mybatis代码

    /**
     * 查询预约信息
     *
     * @param id 预约信息主键
     * @return 预约信息
     */
    @Override
    public Appointment selectAppointmentById(Long id) {
        return appointmentMapper.selectAppointmentById(id);
    }

    /**
     * 查询预约信息列表
     *
     * @param appointment 预约信息
     * @return 预约信息
     */
    @Override
    public List<Appointment> selectAppointmentList(Appointment appointment) {
        List<Appointment> appointments = appointmentMapper.selectAppointmentList(appointment);
        for (Appointment info : appointments) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            Classroom classroom = classroomService.selectClassroomById(info.getClassroomId());
            if (StringUtils.isNotNull(classroom)) {
                info.setClassroomName(classroom.getName());
            }
            Lecture lecture = lectureService.selectLectureById(info.getLectureId());
            if (StringUtils.isNotNull(lecture)) {
                info.setLectureName(lecture.getName());
            }
            SysUser teacherUser = sysUserService.selectUserById(info.getTeacherId());
            if (StringUtils.isNotNull(teacherUser)) {
                info.setTeacherName(teacherUser.getUserName());
            }
            SysUser auditUser = sysUserService.selectUserById(info.getAuditUserId());
            if (StringUtils.isNotNull(auditUser)) {
                info.setAuditUserName(auditUser.getUserName());
            }
        }
        return appointments;
    }

    /**
     * 新增预约信息
     *
     * @param appointment 预约信息
     * @return 结果
     */
    @Override
    public int insertAppointment(Appointment appointment) {
        appointment.setUserId(SecurityUtils.getUserId());
        //查询讲座是否存在且为待开始
        Lecture lecture = lectureService.selectLectureById(appointment.getLectureId());
        ThrowUtils.throwIf(StringUtils.isNull(lecture)
                        || !lecture.getStatus().equals(LectureStatusEnum.LECTURE_STATUS_2.getValue()),
                "当前讲座不可预约");
        //判断用户是否预约
        List<Appointment> appointments = this.selectAppointmentByUserAndLecture(appointment.getUserId(), appointment.getLectureId());
        ThrowUtils.throwIf(StringUtils.isNotEmpty(appointments),
                "您当前已经预约");
        appointment.setStatus(AppointmentStatusEnum.APPOINTMENT_STATUS_1.getValue());
        appointment.setClassroomId(lecture.getClassroomId());
        appointment.setTeacherId(lecture.getTeacherId());
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
    public int updateAppointment(Appointment appointment) {
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
    public int deleteAppointmentByIds(Long[] ids) {
        return appointmentMapper.deleteAppointmentByIds(ids);
    }

    /**
     * 删除预约信息信息
     *
     * @param id 预约信息主键
     * @return 结果
     */
    @Override
    public int deleteAppointmentById(Long id) {
        return appointmentMapper.deleteAppointmentById(id);
    }

    //endregion
    @Override
    public QueryWrapper<Appointment> getQueryWrapper(AppointmentQuery appointmentQuery) {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = appointmentQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = appointmentQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long classroomId = appointmentQuery.getClassroomId();
        queryWrapper.eq(StringUtils.isNotNull(classroomId), "classroom_id", classroomId);

        Long lectureId = appointmentQuery.getLectureId();
        queryWrapper.eq(StringUtils.isNotNull(lectureId), "lecture_id", lectureId);

        Long teacherId = appointmentQuery.getTeacherId();
        queryWrapper.eq(StringUtils.isNotNull(teacherId), "teacher_id", teacherId);

        String status = appointmentQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long auditUserId = appointmentQuery.getAuditUserId();
        queryWrapper.eq(StringUtils.isNotNull(auditUserId), "audit_user_id", auditUserId);

        Long userId = appointmentQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = appointmentQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<AppointmentVo> convertVoList(List<Appointment> appointmentList) {
        if (StringUtils.isEmpty(appointmentList)) {
            return Collections.emptyList();
        }
        return appointmentList.stream().map(AppointmentVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> selectAppointmentByUserAndLecture(Long userId, Long lectureId) {
        return this.list(new LambdaQueryWrapper<Appointment>()
                .eq(Appointment::getUserId, userId)
                .eq(Appointment::getLectureId, lectureId));
    }

}
