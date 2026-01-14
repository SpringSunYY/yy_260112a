package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Sign;
import com.lz.manage.model.vo.sign.SignVo;
import com.lz.manage.model.dto.sign.SignQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 签到信息Service接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface ISignService extends IService<Sign>
{
    //region mybatis代码
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
     * 批量删除签到信息
     * 
     * @param ids 需要删除的签到信息主键集合
     * @return 结果
     */
    public int deleteSignByIds(Long[] ids);

    /**
     * 删除签到信息信息
     * 
     * @param id 签到信息主键
     * @return 结果
     */
    public int deleteSignById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param signQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Sign> getQueryWrapper(SignQuery signQuery);

    /**
     * 转换vo
     *
     * @param signList Sign集合
     * @return SignVO集合
     */
    List<SignVo> convertVoList(List<Sign> signList);
}
