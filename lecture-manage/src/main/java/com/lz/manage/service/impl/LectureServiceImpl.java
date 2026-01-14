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
import com.lz.manage.mapper.LectureMapper;
import com.lz.manage.model.domain.Lecture;
import com.lz.manage.service.ILectureService;
import com.lz.manage.model.dto.lecture.LectureQuery;
import com.lz.manage.model.vo.lecture.LectureVo;

/**
 * 讲座信息Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class LectureServiceImpl extends ServiceImpl<LectureMapper, Lecture> implements ILectureService
{

    @Resource
    private LectureMapper lectureMapper;

    //region mybatis代码
    /**
     * 查询讲座信息
     *
     * @param id 讲座信息主键
     * @return 讲座信息
     */
    @Override
    public Lecture selectLectureById(Long id)
    {
        return lectureMapper.selectLectureById(id);
    }

    /**
     * 查询讲座信息列表
     *
     * @param lecture 讲座信息
     * @return 讲座信息
     */
    @Override
    public List<Lecture> selectLectureList(Lecture lecture)
    {
        return lectureMapper.selectLectureList(lecture);
    }

    /**
     * 新增讲座信息
     *
     * @param lecture 讲座信息
     * @return 结果
     */
    @Override
    public int insertLecture(Lecture lecture)
    {
        lecture.setCreateTime(DateUtils.getNowDate());
        return lectureMapper.insertLecture(lecture);
    }

    /**
     * 修改讲座信息
     *
     * @param lecture 讲座信息
     * @return 结果
     */
    @Override
    public int updateLecture(Lecture lecture)
    {
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
    public int deleteLectureByIds(Long[] ids)
    {
        return lectureMapper.deleteLectureByIds(ids);
    }

    /**
     * 删除讲座信息信息
     *
     * @param id 讲座信息主键
     * @return 结果
     */
    @Override
    public int deleteLectureById(Long id)
    {
        return lectureMapper.deleteLectureById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Lecture> getQueryWrapper(LectureQuery lectureQuery){
        QueryWrapper<Lecture> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = lectureQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = lectureQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long classroomId = lectureQuery.getClassroomId();
        queryWrapper.eq( StringUtils.isNotNull(classroomId),"classroom_id",classroomId);

        String name = lectureQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        Long teacherId = lectureQuery.getTeacherId();
        queryWrapper.eq( StringUtils.isNotNull(teacherId),"teacher_id",teacherId);

        Date lectureStartTime = lectureQuery.getLectureStartTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginLectureStartTime"))&&StringUtils.isNotNull(params.get("endLectureStartTime")),"lecture_start_time",params.get("beginLectureStartTime"),params.get("endLectureStartTime"));

        String status = lectureQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Long userId = lectureQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = lectureQuery.getCreateTime();
        queryWrapper.eq( StringUtils.isNotNull(createTime),"create_time",createTime);

        return queryWrapper;
    }

    @Override
    public List<LectureVo> convertVoList(List<Lecture> lectureList) {
        if (StringUtils.isEmpty(lectureList)) {
            return Collections.emptyList();
        }
        return lectureList.stream().map(LectureVo::objToVo).collect(Collectors.toList());
    }

}
