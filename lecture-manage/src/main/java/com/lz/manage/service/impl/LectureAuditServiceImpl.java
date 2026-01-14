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
import com.lz.manage.mapper.LectureAuditMapper;
import com.lz.manage.model.domain.LectureAudit;
import com.lz.manage.service.ILectureAuditService;
import com.lz.manage.model.dto.lectureAudit.LectureAuditQuery;
import com.lz.manage.model.vo.lectureAudit.LectureAuditVo;

/**
 * 讲座审核Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class LectureAuditServiceImpl extends ServiceImpl<LectureAuditMapper, LectureAudit> implements ILectureAuditService
{

    @Resource
    private LectureAuditMapper lectureAuditMapper;

    //region mybatis代码
    /**
     * 查询讲座审核
     *
     * @param id 讲座审核主键
     * @return 讲座审核
     */
    @Override
    public LectureAudit selectLectureAuditById(Long id)
    {
        return lectureAuditMapper.selectLectureAuditById(id);
    }

    /**
     * 查询讲座审核列表
     *
     * @param lectureAudit 讲座审核
     * @return 讲座审核
     */
    @Override
    public List<LectureAudit> selectLectureAuditList(LectureAudit lectureAudit)
    {
        return lectureAuditMapper.selectLectureAuditList(lectureAudit);
    }

    /**
     * 新增讲座审核
     *
     * @param lectureAudit 讲座审核
     * @return 结果
     */
    @Override
    public int insertLectureAudit(LectureAudit lectureAudit)
    {
        lectureAudit.setCreateTime(DateUtils.getNowDate());
        return lectureAuditMapper.insertLectureAudit(lectureAudit);
    }

    /**
     * 修改讲座审核
     *
     * @param lectureAudit 讲座审核
     * @return 结果
     */
    @Override
    public int updateLectureAudit(LectureAudit lectureAudit)
    {
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
    public int deleteLectureAuditByIds(Long[] ids)
    {
        return lectureAuditMapper.deleteLectureAuditByIds(ids);
    }

    /**
     * 删除讲座审核信息
     *
     * @param id 讲座审核主键
     * @return 结果
     */
    @Override
    public int deleteLectureAuditById(Long id)
    {
        return lectureAuditMapper.deleteLectureAuditById(id);
    }
    //endregion
    @Override
    public QueryWrapper<LectureAudit> getQueryWrapper(LectureAuditQuery lectureAuditQuery){
        QueryWrapper<LectureAudit> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = lectureAuditQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = lectureAuditQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long classroomId = lectureAuditQuery.getClassroomId();
        queryWrapper.eq( StringUtils.isNotNull(classroomId),"classroom_id",classroomId);

        Long lectureId = lectureAuditQuery.getLectureId();
        queryWrapper.eq( StringUtils.isNotNull(lectureId),"lecture_id",lectureId);

        Long teacherId = lectureAuditQuery.getTeacherId();
        queryWrapper.eq( StringUtils.isNotNull(teacherId),"teacher_id",teacherId);

        String status = lectureAuditQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Long userId = lectureAuditQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = lectureAuditQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

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
