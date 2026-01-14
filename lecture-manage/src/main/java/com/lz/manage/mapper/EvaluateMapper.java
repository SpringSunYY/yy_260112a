package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Evaluate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 评价信息Mapper接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface EvaluateMapper extends BaseMapper<Evaluate>
{
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
     * 删除评价信息
     * 
     * @param id 评价信息主键
     * @return 结果
     */
    public int deleteEvaluateById(Long id);

    /**
     * 批量删除评价信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEvaluateByIds(Long[] ids);
}
