package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.SignMapper;
import com.lz.manage.model.domain.Appointment;
import com.lz.manage.model.domain.Classroom;
import com.lz.manage.model.domain.Lecture;
import com.lz.manage.model.domain.Sign;
import com.lz.manage.model.dto.sign.SignQuery;
import com.lz.manage.model.enums.AppointmentStatusEnum;
import com.lz.manage.model.enums.LectureStatusEnum;
import com.lz.manage.model.vo.sign.SignVo;
import com.lz.manage.service.IAppointmentService;
import com.lz.manage.service.IClassroomService;
import com.lz.manage.service.ILectureService;
import com.lz.manage.service.ISignService;
import com.lz.system.service.ISysUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 签到信息Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements ISignService {

    @Resource
    private SignMapper signMapper;

    @Resource
    @Lazy
    private ILectureService lectureService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IClassroomService classroomService;

    @Resource
    private IAppointmentService appointmentService;

    //region mybatis代码

    /**
     * 查询签到信息
     *
     * @param id 签到信息主键
     * @return 签到信息
     */
    @Override
    public Sign selectSignById(Long id) {
        return signMapper.selectSignById(id);
    }

    /**
     * 查询签到信息列表
     *
     * @param sign 签到信息
     * @return 签到信息
     */
    @Override
    public List<Sign> selectSignList(Sign sign) {
        List<Sign> signs = signMapper.selectSignList(sign);
        for (Sign info : signs) {
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
        }
        return signs;
    }

    /**
     * 新增签到信息
     *
     * @param sign 签到信息
     * @return 结果
     */
    @Override
    public int insertSign(Sign sign) {
        sign.setUserId(SecurityUtils.getUserId());
        //查询预约
        List<Appointment> appointments = appointmentService.selectAppointmentByUserAndLecture(sign.getUserId(), sign.getLectureId());
        ThrowUtils.throwIf(StringUtils.isEmpty(appointments),
                "预约信息不存在");
        Appointment appointment = appointments.get(0);
        ThrowUtils.throwIf(!appointment.getStatus().equals(AppointmentStatusEnum.APPOINTMENT_STATUS_2.getValue()),
                "您的预约并没有通过");
        //查询讲座
        Lecture lecture = lectureService.selectLectureById(sign.getLectureId());
        ThrowUtils.throwIf(StringUtils.isNull(lecture), "讲座不存在");
        //时间是否在开始结束时间内
        Date nowDate = DateUtils.getNowDate();
        ThrowUtils.throwIf(
                lecture.getLectureStartTime().after(nowDate)
                        || lecture.getLectureEndTime().before(nowDate),
                "讲座时间未开始或已结束"
        );
        //如果已经签到
        List<Sign> signList = this.list(new LambdaQueryWrapper<Sign>()
                .eq(Sign::getUserId, sign.getUserId())
                .eq(Sign::getLectureId, sign.getLectureId()));
        ThrowUtils.throwIf(
                StringUtils.isNotEmpty(signList),
                "您已经签到"
        );
        sign.setClassroomId(lecture.getClassroomId());
        sign.setTeacherId(lecture.getTeacherId());
        int i = signMapper.insertSign(sign);
        //签到人数
        lecture.setPeopleSignNumber(this.count(new LambdaQueryWrapper<Sign>()
                .eq(Sign::getLectureId, sign.getLectureId())));
        lectureService.updateById(lecture);
        return i;
    }

    /**
     * 修改签到信息
     *
     * @param sign 签到信息
     * @return 结果
     */
    @Override
    public int updateSign(Sign sign) {
        sign.setUpdateTime(DateUtils.getNowDate());
        return signMapper.updateSign(sign);
    }

    /**
     * 批量删除签到信息
     *
     * @param ids 需要删除的签到信息主键
     * @return 结果
     */
    @Override
    public int deleteSignByIds(Long[] ids) {
        List<Lecture> lectures = new ArrayList<>();
        for (Long id : ids) {
            Sign sign = this.selectSignById(id);
            ThrowUtils.throwIf(StringUtils.isNull(sign), StringUtils.format("当前预约信息:{}，不存在", id));
            Lecture lecture = lectureService.selectLectureById(sign.getLectureId());
            ThrowUtils.throwIf(StringUtils.isNull(lecture), StringUtils.format("当前讲座:{}，不存在", lecture.getName()));
            ThrowUtils.throwIf(lecture.getStatus().equals(LectureStatusEnum.LECTURE_STATUS_4.getValue()),
                    StringUtils.format("当前讲座:{}，无法删除预约信息", lecture.getName()));
            lectures.add(lecture);
        }
        int i = signMapper.deleteSignByIds(ids);
        for (Lecture lecture : lectures) {
            lecture.setPeopleJoinNumber(this.count(new LambdaQueryWrapper<Sign>().eq(Sign::getLectureId, lecture.getId())));
        }
        lectureService.updateBatchById(lectures);
        return i;
    }

    /**
     * 删除签到信息信息
     *
     * @param id 签到信息主键
     * @return 结果
     */
    @Override
    public int deleteSignById(Long id) {
        return signMapper.deleteSignById(id);
    }

    //endregion
    @Override
    public QueryWrapper<Sign> getQueryWrapper(SignQuery signQuery) {
        QueryWrapper<Sign> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = signQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = signQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long classroomId = signQuery.getClassroomId();
        queryWrapper.eq(StringUtils.isNotNull(classroomId), "classroom_id", classroomId);

        Long lectureId = signQuery.getLectureId();
        queryWrapper.eq(StringUtils.isNotNull(lectureId), "lecture_id", lectureId);

        Long teacherId = signQuery.getTeacherId();
        queryWrapper.eq(StringUtils.isNotNull(teacherId), "teacher_id", teacherId);

        String name = signQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        Long userId = signQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = signQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<SignVo> convertVoList(List<Sign> signList) {
        if (StringUtils.isEmpty(signList)) {
            return Collections.emptyList();
        }
        return signList.stream().map(SignVo::objToVo).collect(Collectors.toList());
    }

}
