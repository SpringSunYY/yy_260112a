package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.LectureAuditMapper;
import com.lz.manage.model.domain.Classroom;
import com.lz.manage.model.domain.Lecture;
import com.lz.manage.model.domain.LectureAudit;
import com.lz.manage.model.dto.lectureAudit.LectureAuditQuery;
import com.lz.manage.model.enums.AuditStatusEnum;
import com.lz.manage.model.enums.LectureStatusEnum;
import com.lz.manage.model.vo.lectureAudit.LectureAuditVo;
import com.lz.manage.service.IClassroomService;
import com.lz.manage.service.ILectureAuditService;
import com.lz.manage.service.ILectureService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 讲座审核Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class LectureAuditServiceImpl extends ServiceImpl<LectureAuditMapper, LectureAudit> implements ILectureAuditService {

    @Resource
    private LectureAuditMapper lectureAuditMapper;


    @Resource
    private ILectureService lectureService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IClassroomService classroomService;
    //region mybatis代码

    /**
     * 查询讲座审核
     *
     * @param id 讲座审核主键
     * @return 讲座审核
     */
    @Override
    public LectureAudit selectLectureAuditById(Long id) {
        return lectureAuditMapper.selectLectureAuditById(id);
    }

    /**
     * 查询讲座审核列表
     *
     * @param lectureAudit 讲座审核
     * @return 讲座审核
     */
    @Override
    public List<LectureAudit> selectLectureAuditList(LectureAudit lectureAudit) {
        List<LectureAudit> lectureAudits = lectureAuditMapper.selectLectureAuditList(lectureAudit);
        for (LectureAudit info : lectureAudits) {
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
            Lecture lecture = lectureService.selectLectureById(info.getLectureId());
            if (StringUtils.isNotNull(lecture)) {
                info.setLectureName(lecture.getName());
            }
        }
        return lectureAudits;
    }

    /**
     * 新增讲座审核
     *
     * @param lectureAudit 讲座审核
     * @return 结果
     */
    @Transactional
    @Override
    public int insertLectureAudit(LectureAudit lectureAudit) {
        //首先查询讲座是否存在
        Lecture lecture = lectureService.selectLectureById(lectureAudit.getLectureId());
        ThrowUtils.throwIf(StringUtils.isNull(lecture), "讲座不存在");
        //如果讲座不是待审核、拒绝的
        ThrowUtils.throwIf(
                !lecture.getStatus().equals(LectureStatusEnum.LECTURE_STATUS_1.getValue())
                        && !lecture.getStatus().equals(LectureStatusEnum.LECTURE_STATUS_5.getValue()),
                "当前讲座无法审核");
        //如果传过来的是同意
        if (lectureAudit.getStatus().equals(AuditStatusEnum.AUDIT_STATUS_1.getValue())) {
            //判断讲座时间范围内是否冲突
            boolean timeConflict = lectureService.isLectureTimeConflict(lecture);
            ThrowUtils.throwIf(timeConflict, "该教室时间冲突，不可同意");
            lecture.setStatus(LectureStatusEnum.LECTURE_STATUS_2.getValue());
        } else {
            lecture.setStatus(LectureStatusEnum.LECTURE_STATUS_5.getValue());
        }
        lectureAudit.setLectureId(lecture.getId());
        lectureAudit.setClassroomId(lecture.getClassroomId());
        lectureAudit.setTeacherId(lecture.getTeacherId());
        lectureAudit.setPeopleNumberLimit(lecture.getPeopleNumberLimit());
        lectureAudit.setUserId(SecurityUtils.getUserId());
        lectureAudit.setCreateTime(DateUtils.getNowDate());
        lectureService.updateLecture(lecture);
        return lectureAuditMapper.insertLectureAudit(lectureAudit);
    }

    /**
     * 修改讲座审核
     *
     * @param lectureAudit 讲座审核
     * @return 结果
     */
    @Override
    public int updateLectureAudit(LectureAudit lectureAudit) {
        lectureAudit.setUpdateTime(DateUtils.getNowDate());
        return lectureAuditMapper.updateLectureAudit(lectureAudit);
    }

    /**
     * 批量删除讲座审核
     *
     * @param ids 需要删除的讲座审核主键
     * @return 结果
     */
    @Override
    public int deleteLectureAuditByIds(Long[] ids) {
        return lectureAuditMapper.deleteLectureAuditByIds(ids);
    }

    /**
     * 删除讲座审核信息
     *
     * @param id 讲座审核主键
     * @return 结果
     */
    @Override
    public int deleteLectureAuditById(Long id) {
        return lectureAuditMapper.deleteLectureAuditById(id);
    }

    //endregion
    @Override
    public QueryWrapper<LectureAudit> getQueryWrapper(LectureAuditQuery lectureAuditQuery) {
        QueryWrapper<LectureAudit> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = lectureAuditQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = lectureAuditQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long classroomId = lectureAuditQuery.getClassroomId();
        queryWrapper.eq(StringUtils.isNotNull(classroomId), "classroom_id", classroomId);

        Long lectureId = lectureAuditQuery.getLectureId();
        queryWrapper.eq(StringUtils.isNotNull(lectureId), "lecture_id", lectureId);

        Long teacherId = lectureAuditQuery.getTeacherId();
        queryWrapper.eq(StringUtils.isNotNull(teacherId), "teacher_id", teacherId);

        String status = lectureAuditQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long userId = lectureAuditQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = lectureAuditQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<LectureAuditVo> convertVoList(List<LectureAudit> lectureAuditList) {
        if (StringUtils.isEmpty(lectureAuditList)) {
            return Collections.emptyList();
        }
        return lectureAuditList.stream().map(LectureAuditVo::objToVo).collect(Collectors.toList());
    }

}
