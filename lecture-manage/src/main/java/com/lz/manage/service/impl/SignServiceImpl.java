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
import com.lz.manage.mapper.SignMapper;
import com.lz.manage.model.domain.Sign;
import com.lz.manage.service.ISignService;
import com.lz.manage.model.dto.sign.SignQuery;
import com.lz.manage.model.vo.sign.SignVo;

/**
 * 签到信息Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements ISignService
{

    @Resource
    private SignMapper signMapper;

    //region mybatis代码
    /**
     * 查询签到信息
     *
     * @param id 签到信息主键
     * @return 签到信息
     */
    @Override
    public Sign selectSignById(Long id)
    {
        return signMapper.selectSignById(id);
    }

    /**
     * 查询签到信息列表
     *
     * @param sign 签到信息
     * @return 签到信息
     */
    @Override
    public List<Sign> selectSignList(Sign sign)
    {
        return signMapper.selectSignList(sign);
    }

    /**
     * 新增签到信息
     *
     * @param sign 签到信息
     * @return 结果
     */
    @Override
    public int insertSign(Sign sign)
    {
        sign.setCreateTime(DateUtils.getNowDate());
        return signMapper.insertSign(sign);
    }

    /**
     * 修改签到信息
     *
     * @param sign 签到信息
     * @return 结果
     */
    @Override
    public int updateSign(Sign sign)
    {
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
    public int deleteSignByIds(Long[] ids)
    {
        return signMapper.deleteSignByIds(ids);
    }

    /**
     * 删除签到信息信息
     *
     * @param id 签到信息主键
     * @return 结果
     */
    @Override
    public int deleteSignById(Long id)
    {
        return signMapper.deleteSignById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Sign> getQueryWrapper(SignQuery signQuery){
        QueryWrapper<Sign> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = signQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = signQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long classroomId = signQuery.getClassroomId();
        queryWrapper.eq( StringUtils.isNotNull(classroomId),"classroom_id",classroomId);

        Long lectureId = signQuery.getLectureId();
        queryWrapper.eq( StringUtils.isNotNull(lectureId),"lecture_id",lectureId);

        Long teacherId = signQuery.getTeacherId();
        queryWrapper.eq( StringUtils.isNotNull(teacherId),"teacher_id",teacherId);

        String name = signQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        Long userId = signQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = signQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

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
