package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Lecture;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 讲座信息Mapper接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface LectureMapper extends BaseMapper<Lecture>
{
    /**
     * 查询讲座信息
     * 
     * @param id 讲座信息主键
     * @return 讲座信息
     */
    public Lecture selectLectureById(Long id);

    /**
     * 查询讲座信息列表
     * 
     * @param lecture 讲座信息
     * @return 讲座信息集合
     */
    public List<Lecture> selectLectureList(Lecture lecture);

    /**
     * 新增讲座信息
     * 
     * @param lecture 讲座信息
     * @return 结果
     */
    public int insertLecture(Lecture lecture);

    /**
     * 修改讲座信息
     * 
     * @param lecture 讲座信息
     * @return 结果
     */
    public int updateLecture(Lecture lecture);

    /**
     * 删除讲座信息
     * 
     * @param id 讲座信息主键
     * @return 结果
     */
    public int deleteLectureById(Long id);

    /**
     * 批量删除讲座信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLectureByIds(Long[] ids);
}
