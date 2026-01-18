package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Appointment;
import com.lz.manage.model.vo.appointment.AppointmentVo;
import com.lz.manage.model.dto.appointment.AppointmentQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 预约信息Service接口
 *
 * @author YY
 * @date 2026-01-14
 */
public interface IAppointmentService extends IService<Appointment>
{
    //region mybatis代码
    /**
     * 查询预约信息
     *
     * @param id 预约信息主键
     * @return 预约信息
     */
    public Appointment selectAppointmentById(Long id);

    /**
     * 查询预约信息列表
     *
     * @param appointment 预约信息
     * @return 预约信息集合
     */
    public List<Appointment> selectAppointmentList(Appointment appointment);

    /**
     * 新增预约信息
     *
     * @param appointment 预约信息
     * @return 结果
     */
    public int insertAppointment(Appointment appointment);

    /**
     * 修改预约信息
     *
     * @param appointment 预约信息
     * @return 结果
     */
    public int updateAppointment(Appointment appointment);

    /**
     * 批量删除预约信息
     *
     * @param ids 需要删除的预约信息主键集合
     * @return 结果
     */
    public int deleteAppointmentByIds(Long[] ids);

    /**
     * 删除预约信息信息
     *
     * @param id 预约信息主键
     * @return 结果
     */
    public int deleteAppointmentById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param appointmentQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Appointment> getQueryWrapper(AppointmentQuery appointmentQuery);

    /**
     * 转换vo
     *
     * @param appointmentList Appointment集合
     * @return AppointmentVO集合
     */
    List<AppointmentVo> convertVoList(List<Appointment> appointmentList);

    /**
     * 根据用户id和讲座id查询预约信息
     *
     * @param userId 用户id
     * @param lectureId 讲座id
     * @return Appointment集合
     */
    List<Appointment> selectAppointmentByUserAndLecture(Long userId, Long lectureId);

    int auditAppointment(Appointment appointment);
}
