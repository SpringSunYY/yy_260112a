package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Evaluate;
import com.lz.manage.model.vo.evaluate.EvaluateVo;
import com.lz.manage.model.dto.evaluate.EvaluateQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 评价信息Service接口
 *
 * @author YY
 * @date 2026-01-14
 */
public interface IEvaluateService extends IService<Evaluate>
{
    //region mybatis代码
    /**
     * 查询评价信息
     *
     * @param id 评价信息主键
     * @return 评价信息
     */
    public Evaluate selectEvaluateById(Long id);

    /**
     * 查询评价信息列表
     *
     * @param evaluate 评价信息
     * @return 评价信息集合
     */
    public List<Evaluate> selectEvaluateList(Evaluate evaluate);


    List<Evaluate> selectEvaluateListByDetail(Evaluate evaluate);

    /**
     * 新增评价信息
     *
     * @param evaluate 评价信息
     * @return 结果
     */
    public int insertEvaluate(Evaluate evaluate);

    /**
     * 修改评价信息
     *
     * @param evaluate 评价信息
     * @return 结果
     */
    public int updateEvaluate(Evaluate evaluate);

    /**
     * 批量删除评价信息
     *
     * @param ids 需要删除的评价信息主键集合
     * @return 结果
     */
    public int deleteEvaluateByIds(Long[] ids);

    /**
     * 删除评价信息信息
     *
     * @param id 评价信息主键
     * @return 结果
     */
    public int deleteEvaluateById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param evaluateQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Evaluate> getQueryWrapper(EvaluateQuery evaluateQuery);

    /**
     * 转换vo
     *
     * @param evaluateList Evaluate集合
     * @return EvaluateVO集合
     */
    List<EvaluateVo> convertVoList(List<Evaluate> evaluateList);
}
