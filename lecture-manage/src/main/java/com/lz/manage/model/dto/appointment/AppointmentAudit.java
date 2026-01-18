package com.lz.manage.model.dto.appointment;

import com.lz.manage.model.domain.Appointment;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 预约信息Vo对象 tb_appointment
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class AppointmentAudit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 讲座 */
    private Long lectureId;


    /** 预约状态 */
    private String status;

    /** 审核描述 */
    private String auditDescription;

    /**
     * 对象转封装类
     *
     * @param appointmentEdit 编辑对象
     * @return Appointment
     */
    public static Appointment editToObj(AppointmentAudit appointmentEdit) {
        if (appointmentEdit == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(appointmentEdit, appointment);
        return appointment;
    }
}
