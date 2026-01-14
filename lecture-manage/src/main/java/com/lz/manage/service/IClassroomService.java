package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Classroom;
import com.lz.manage.model.vo.classroom.ClassroomVo;
import com.lz.manage.model.dto.classroom.ClassroomQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 教室信息Service接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface IClassroomService extends IService<Classroom>
{
    //region mybatis代码
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
     * 批量删除教室信息
     * 
     * @param ids 需要删除的教室信息主键集合
     * @return 结果
     */
    public int deleteClassroomByIds(Long[] ids);

    /**
     * 删除教室信息信息
     * 
     * @param id 教室信息主键
     * @return 结果
     */
    public int deleteClassroomById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param classroomQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Classroom> getQueryWrapper(ClassroomQuery classroomQuery);

    /**
     * 转换vo
     *
     * @param classroomList Classroom集合
     * @return ClassroomVO集合
     */
    List<ClassroomVo> convertVoList(List<Classroom> classroomList);
}
