package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Sign;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 签到信息Mapper接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface SignMapper extends BaseMapper<Sign>
{
    /**
     * 查询签到信息
     * 
     * @param id 签到信息主键
     * @return 签到信息
     */
    public Sign selectSignById(Long id);

    /**
     * 查询签到信息列表
     * 
     * @param sign 签到信息
     * @return 签到信息集合
     */
    public List<Sign> selectSignList(Sign sign);

    /**
     * 新增签到信息
     * 
     * @param sign 签到信息
     * @return 结果
     */
    public int insertSign(Sign sign);

    /**
     * 修改签到信息
     * 
     * @param sign 签到信息
     * @return 结果
     */
    public int updateSign(Sign sign);

    /**
     * 删除签到信息
     * 
     * @param id 签到信息主键
     * @return 结果
     */
    public int deleteSignById(Long id);

    /**
     * 批量删除签到信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSignByIds(Long[] ids);
}
