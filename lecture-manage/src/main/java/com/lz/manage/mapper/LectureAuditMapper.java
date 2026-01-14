package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.LectureAudit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 讲座审核Mapper接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface LectureAuditMapper extends BaseMapper<LectureAudit>
{
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
     * 删除讲座审核
     * 
     * @param id 讲座审核主键
     * @return 结果
     */
    public int deleteLectureAuditById(Long id);

    /**
     * 批量删除讲座审核
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLectureAuditByIds(Long[] ids);
}
