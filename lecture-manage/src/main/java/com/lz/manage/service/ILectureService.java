package com.lz.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.manage.model.domain.Lecture;
import com.lz.manage.model.dto.lecture.LectureQuery;
import com.lz.manage.model.vo.lecture.LectureVo;

import java.util.List;

/**
 * 讲座信息Service接口
 *
 * @author YY
 * @date 2026-01-14
 */
public interface ILectureService extends IService<Lecture> {
    //region mybatis代码

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
     * 判断教室时间冲突
     *
     * @param lecture 讲座信息
     * @return boolean
     * @author: YY
     * @method: isLectureTimeConflict
     * @date: 2026/1/17 19:14
     **/
    boolean isLectureTimeConflict(Lecture lecture);

    /**
     * 修改讲座信息
     *
     * @param lecture 讲座信息
     * @return 结果
     */
    public int updateLecture(Lecture lecture);

    /**
     * 批量删除讲座信息
     *
     * @param ids 需要删除的讲座信息主键集合
     * @return 结果
     */
    public int deleteLectureByIds(Long[] ids);

    /**
     * 删除讲座信息信息
     *
     * @param id 讲座信息主键
     * @return 结果
     */
    public int deleteLectureById(Long id);
    //endregion

    /**
     * 获取查询条件
     *
     * @param lectureQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Lecture> getQueryWrapper(LectureQuery lectureQuery);

    /**
     * 转换vo
     *
     * @param lectureList Lecture集合
     * @return LectureVO集合
     */
    List<LectureVo> convertVoList(List<Lecture> lectureList);
}
