package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.LectureAudit;
import com.lz.manage.model.vo.lectureAudit.LectureAuditVo;
import com.lz.manage.model.dto.lectureAudit.LectureAuditQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 讲座审核Service接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface ILectureAuditService extends IService<LectureAudit>
{
    //region mybatis代码
    /**
     * 查询讲座审核
     * 
     * @param id 讲座审核主键
     * @return 讲座审核
     */
    public LectureAudit selectLectureAuditById(Long id);

    /**
     * 查询讲座审核列表
     * 
     * @param lectureAudit 讲座审核
     * @return 讲座审核集合
     */
    public List<LectureAudit> selectLectureAuditList(LectureAudit lectureAudit);

    /**
     * 新增讲座审核
     * 
     * @param lectureAudit 讲座审核
     * @return 结果
     */
    public int insertLectureAudit(LectureAudit lectureAudit);

    /**
     * 修改讲座审核
     * 
     * @param lectureAudit 讲座审核
     * @return 结果
     */
    public int updateLectureAudit(LectureAudit lectureAudit);

    /**
     * 批量删除讲座审核
     * 
     * @param ids 需要删除的讲座审核主键集合
     * @return 结果
     */
    public int deleteLectureAuditByIds(Long[] ids);

    /**
     * 删除讲座审核信息
     * 
     * @param id 讲座审核主键
     * @return 结果
     */
    public int deleteLectureAuditById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param lectureAuditQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<LectureAudit> getQueryWrapper(LectureAuditQuery lectureAuditQuery);

    /**
     * 转换vo
     *
     * @param lectureAuditList LectureAudit集合
     * @return LectureAuditVO集合
     */
    List<LectureAuditVo> convertVoList(List<LectureAudit> lectureAuditList);
}
