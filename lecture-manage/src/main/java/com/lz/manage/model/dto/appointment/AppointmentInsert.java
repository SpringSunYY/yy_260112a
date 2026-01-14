package com.lz.manage.model.dto.appointment;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Appointment;
/**
 * 预约信息Vo对象 tb_appointment
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class AppointmentInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 讲座 */
    private Long lectureId;

    /** 预约描述 */
    private String appointmentDescription;

    /** 预约状态 */
    private String status;

    /** 审核描述 */
    private String auditDescription;

    /** 备注 */
    private String remark;

    /** 预约人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param appointmentInsert 插入对象
     * @return AppointmentInsert
     */
    public static Appointment insertToObj(AppointmentInsert appointmentInsert) {
        if (appointmentInsert == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(appointmentInsert, appointment);
        return appointment;
    }
}
