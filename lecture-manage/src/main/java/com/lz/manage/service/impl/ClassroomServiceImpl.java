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
import com.lz.manage.mapper.ClassroomMapper;
import com.lz.manage.model.domain.Classroom;
import com.lz.manage.service.IClassroomService;
import com.lz.manage.model.dto.classroom.ClassroomQuery;
import com.lz.manage.model.vo.classroom.ClassroomVo;

/**
 * 教室信息Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements IClassroomService
{

    @Resource
    private ClassroomMapper classroomMapper;

    //region mybatis代码
    /**
     * 查询教室信息
     *
     * @param id 教室信息主键
     * @return 教室信息
     */
    @Override
    public Classroom selectClassroomById(Long id)
    {
        return classroomMapper.selectClassroomById(id);
    }

    /**
     * 查询教室信息列表
     *
     * @param classroom 教室信息
     * @return 教室信息
     */
    @Override
    public List<Classroom> selectClassroomList(Classroom classroom)
    {
        return classroomMapper.selectClassroomList(classroom);
    }

    /**
     * 新增教室信息
     *
     * @param classroom 教室信息
     * @return 结果
     */
    @Override
    public int insertClassroom(Classroom classroom)
    {
        classroom.setCreateTime(DateUtils.getNowDate());
        return classroomMapper.insertClassroom(classroom);
    }

    /**
     * 修改教室信息
     *
     * @param classroom 教室信息
     * @return 结果
     */
    @Override
    public int updateClassroom(Classroom classroom)
    {
        classroom.setUpdateTime(DateUtils.getNowDate());
        return classroomMapper.updateClassroom(classroom);
    }

    /**
     * 批量删除教室信息
     *
     * @param ids 需要删除的教室信息主键
     * @return 结果
     */
    @Override
    public int deleteClassroomByIds(Long[] ids)
    {
        return classroomMapper.deleteClassroomByIds(ids);
    }

    /**
     * 删除教室信息信息
     *
     * @param id 教室信息主键
     * @return 结果
     */
    @Override
    public int deleteClassroomById(Long id)
    {
        return classroomMapper.deleteClassroomById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Classroom> getQueryWrapper(ClassroomQuery classroomQuery){
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = classroomQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = classroomQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String name = classroomQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        String status = classroomQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Long userId = classroomQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = classroomQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<ClassroomVo> convertVoList(List<Classroom> classroomList) {
        if (StringUtils.isEmpty(classroomList)) {
            return Collections.emptyList();
        }
        return classroomList.stream().map(ClassroomVo::objToVo).collect(Collectors.toList());
    }

}
