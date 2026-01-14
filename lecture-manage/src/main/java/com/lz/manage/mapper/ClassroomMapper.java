package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Classroom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 教室信息Mapper接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface ClassroomMapper extends BaseMapper<Classroom>
{
    /**
     * 查询教室信息
     * 
     * @param id 教室信息主键
     * @return 教室信息
     */
    public Classroom selectClassroomById(Long id);

    /**
     * 查询教室信息列表
     * 
     * @param classroom 教室信息
     * @return 教室信息集合
     */
    public List<Classroom> selectClassroomList(Classroom classroom);

    /**
     * 新增教室信息
     * 
     * @param classroom 教室信息
     * @return 结果
     */
    public int insertClassroom(Classroom classroom);

    /**
     * 修改教室信息
     * 
     * @param classroom 教室信息
     * @return 结果
     */
    public int updateClassroom(Classroom classroom);

    /**
     * 删除教室信息
     * 
     * @param id 教室信息主键
     * @return 结果
     */
    public int deleteClassroomById(Long id);

    /**
     * 批量删除教室信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClassroomByIds(Long[] ids);
}
