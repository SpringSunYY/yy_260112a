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
import com.lz.manage.mapper.EvaluateMapper;
import com.lz.manage.model.domain.Evaluate;
import com.lz.manage.service.IEvaluateService;
import com.lz.manage.model.dto.evaluate.EvaluateQuery;
import com.lz.manage.model.vo.evaluate.EvaluateVo;

/**
 * 评价信息Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements IEvaluateService
{

    @Resource
    private EvaluateMapper evaluateMapper;

    //region mybatis代码
    /**
     * 查询评价信息
     *
     * @param id 评价信息主键
     * @return 评价信息
     */
    @Override
    public Evaluate selectEvaluateById(Long id)
    {
        return evaluateMapper.selectEvaluateById(id);
    }

    /**
     * 查询评价信息列表
     *
     * @param evaluate 评价信息
     * @return 评价信息
     */
    @Override
    public List<Evaluate> selectEvaluateList(Evaluate evaluate)
    {
        return evaluateMapper.selectEvaluateList(evaluate);
    }

    /**
     * 新增评价信息
     *
     * @param evaluate 评价信息
     * @return 结果
     */
    @Override
    public int insertEvaluate(Evaluate evaluate)
    {
        evaluate.setCreateTime(DateUtils.getNowDate());
        return evaluateMapper.insertEvaluate(evaluate);
    }

    /**
     * 修改评价信息
     *
     * @param evaluate 评价信息
     * @return 结果
     */
    @Override
    public int updateEvaluate(Evaluate evaluate)
    {
        evaluate.setUpdateTime(DateUtils.getNowDate());
        return evaluateMapper.updateEvaluate(evaluate);
    }

    /**
     * 批量删除评价信息
     *
     * @param ids 需要删除的评价信息主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateByIds(Long[] ids)
    {
        return evaluateMapper.deleteEvaluateByIds(ids);
    }

    /**
     * 删除评价信息信息
     *
     * @param id 评价信息主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateById(Long id)
    {
        return evaluateMapper.deleteEvaluateById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Evaluate> getQueryWrapper(EvaluateQuery evaluateQuery){
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = evaluateQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = evaluateQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long classroomId = evaluateQuery.getClassroomId();
        queryWrapper.eq( StringUtils.isNotNull(classroomId),"classroom_id",classroomId);

        Long lectureId = evaluateQuery.getLectureId();
        queryWrapper.eq( StringUtils.isNotNull(lectureId),"lecture_id",lectureId);

        Long teacherId = evaluateQuery.getTeacherId();
        queryWrapper.eq( StringUtils.isNotNull(teacherId),"teacher_id",teacherId);

        String title = evaluateQuery.getTitle();
        queryWrapper.like(StringUtils.isNotEmpty(title) ,"title",title);

        Long userId = evaluateQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = evaluateQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<EvaluateVo> convertVoList(List<Evaluate> evaluateList) {
        if (StringUtils.isEmpty(evaluateList)) {
            return Collections.emptyList();
        }
        return evaluateList.stream().map(EvaluateVo::objToVo).collect(Collectors.toList());
    }

}
