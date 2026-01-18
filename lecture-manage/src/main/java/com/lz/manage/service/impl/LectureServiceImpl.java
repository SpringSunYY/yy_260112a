package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.LectureMapper;
import com.lz.manage.model.domain.Appointment;
import com.lz.manage.model.domain.Classroom;
import com.lz.manage.model.domain.Lecture;
import com.lz.manage.model.dto.lecture.LectureQuery;
import com.lz.manage.model.enums.ClassroomStatusEnum;
import com.lz.manage.model.enums.LectureStatusEnum;
import com.lz.manage.model.vo.lecture.LectureVo;
import com.lz.manage.service.IAppointmentService;
import com.lz.manage.service.IClassroomService;
import com.lz.manage.service.ILectureService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 讲座信息Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class LectureServiceImpl extends ServiceImpl<LectureMapper, Lecture> implements ILectureService {

    @Resource
    private LectureMapper lectureMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IClassroomService classroomService;

    @Resource
    private IAppointmentService appointmentService;

    //region mybatis代码

    /**
     * 查询讲座信息
     *
     * @param id 讲座信息主键
     * @return 讲座信息
     */
    @Override
    public Lecture selectLectureById(Long id) {
        return lectureMapper.selectLectureById(id);
    }

    /**
     * 查询讲座信息列表
     *
     * @param lecture 讲座信息
     * @return 讲座信息
     */
    @Override
    public List<Lecture> selectLectureList(Lecture lecture) {
        List<Lecture> lectures = lectureMapper.selectLectureList(lecture);
        for (Lecture info : lectures) {
            initLectureInfo(info);
        }
        return lectures;
    }

    /**
     * 新增讲座信息
     *
     * @param lecture 讲座信息
     * @return 结果
     */
    @Override
    public int insertLecture(Lecture lecture) {
        //判断开始时间是否小于结束时间
        ThrowUtils.throwIf(lecture.getLectureStartTime().after(lecture.getLectureEndTime()),
                "开始时间不能大于结束时间");
        //查询教室是否存在且正常
        Classroom classroom = classroomService.selectClassroomById(lecture.getClassroomId());
        ThrowUtils.throwIf(StringUtils.isNull(classroom)
                        || classroom.getStatus().equals(ClassroomStatusEnum.CLASSROOM_STATUS_2.getValue()),
                "教室不存在或者异常");
        //查询讲师是否存在
        SysUser teacherUser = sysUserService.selectUserById(lecture.getTeacherId());
        ThrowUtils.throwIf(StringUtils.isNull(teacherUser), "讲师不存在");
        boolean conflict = isLectureTimeConflict(lecture);
        ThrowUtils.throwIf(conflict, "教室时间冲突，不可申请该教室");

        //初始化状态
        lecture.setStatus(LectureStatusEnum.LECTURE_STATUS_1.getValue());
        lecture.setUserId(SecurityUtils.getUserId());
        lecture.setCreateTime(DateUtils.getNowDate());
        return lectureMapper.insertLecture(lecture);
    }

    @Override
    public boolean isLectureTimeConflict(Lecture lecture) {
        // 判断开始结束时间范围内是否有其他讲座，待审核的不需要管
        LambdaQueryWrapper<Lecture> queryWrapper = new LambdaQueryWrapper<>();
        ArrayList<String> statusList = new ArrayList<>();
        statusList.add(LectureStatusEnum.LECTURE_STATUS_3.getValue());
        statusList.add(LectureStatusEnum.LECTURE_STATUS_2.getValue());
        queryWrapper
                .in(Lecture::getStatus, statusList)
                .eq(Lecture::getClassroomId, lecture.getClassroomId())
                .gt(Lecture::getLectureEndTime, lecture.getLectureStartTime())   // 现有结束 > 新开始
                .lt(Lecture::getLectureStartTime, lecture.getLectureEndTime());  // 现有开始 < 新结束

        List<Lecture> existingLectures = this.list(queryWrapper);
        return !existingLectures.isEmpty();
    }

    /**
     * 修改讲座信息
     *
     * @param lecture 讲座信息
     * @return 结果
     */
    @Override
    public int updateLecture(Lecture lecture) {
        //判断开始时间是否小于结束时间
        ThrowUtils.throwIf(lecture.getLectureStartTime().after(lecture.getLectureEndTime()),
                "开始时间不能大于结束时间");
        //判断是否已结束
        ThrowUtils.throwIf(lecture.getStatus().equals(LectureStatusEnum.LECTURE_STATUS_4.getValue()),
                "讲座已结束不可再次修改");
        lecture.setUpdateTime(DateUtils.getNowDate());
        return lectureMapper.updateLecture(lecture);
    }

    /**
     * 批量删除讲座信息
     *
     * @param ids 需要删除的讲座信息主键
     * @return 结果
     */
    @Override
    public int deleteLectureByIds(Long[] ids) {
        return lectureMapper.deleteLectureByIds(ids);
    }

    /**
     * 删除讲座信息信息
     *
     * @param id 讲座信息主键
     * @return 结果
     */
    @Override
    public int deleteLectureById(Long id) {
        return lectureMapper.deleteLectureById(id);
    }

    //endregion
    @Override
    public QueryWrapper<Lecture> getQueryWrapper(LectureQuery lectureQuery) {
        QueryWrapper<Lecture> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = lectureQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = lectureQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long classroomId = lectureQuery.getClassroomId();
        queryWrapper.eq(StringUtils.isNotNull(classroomId), "classroom_id", classroomId);

        String name = lectureQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        Long teacherId = lectureQuery.getTeacherId();
        queryWrapper.eq(StringUtils.isNotNull(teacherId), "teacher_id", teacherId);

        Date lectureStartTime = lectureQuery.getLectureStartTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginLectureStartTime")) && StringUtils.isNotNull(params.get("endLectureStartTime")), "lecture_start_time", params.get("beginLectureStartTime"), params.get("endLectureStartTime"));

        String status = lectureQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long userId = lectureQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = lectureQuery.getCreateTime();
        queryWrapper.eq(StringUtils.isNotNull(createTime), "create_time", createTime);

        return queryWrapper;
    }

    @Override
    public List<LectureVo> convertVoList(List<Lecture> lectureList) {
        if (StringUtils.isEmpty(lectureList)) {
            return Collections.emptyList();
        }
        return lectureList.stream().map(LectureVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public List<Lecture> selectLectureListHome(Lecture lecture) {
        Long userId = SecurityUtils.getUserId();

        List<Lecture> lectures = lectureMapper.selectLectureList(lecture);
        for (Lecture info : lectures) {
            initLectureInfo(info);
            List<Appointment> appointmentList = appointmentService.selectAppointmentByUserAndLecture(userId, info.getId());
            info.setIsAppointment(StringUtils.isNotEmpty(appointmentList));
        }
        return lectures;
    }

    private void initLectureInfo(Lecture info) {
        SysUser sysUser = sysUserService.selectUserById(info.getUserId());
        if (StringUtils.isNotNull(sysUser)) {
            info.setUserName(sysUser.getUserName());
        }
        Classroom classroom = classroomService.selectClassroomById(info.getClassroomId());
        if (StringUtils.isNotNull(classroom)) {
            info.setClassroomName(classroom.getName());
        }
        SysUser teacherUser = sysUserService.selectUserById(info.getTeacherId());
        if (StringUtils.isNotNull(teacherUser)) {
            info.setTeacherName(teacherUser.getUserName());
        }
    }

}
