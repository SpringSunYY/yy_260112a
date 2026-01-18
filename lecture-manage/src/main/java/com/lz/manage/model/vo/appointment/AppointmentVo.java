package com.lz.manage.model.vo.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Appointment;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约信息Vo对象 tb_appointment
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class AppointmentVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 教室
     */
    private Long classroomId;
    private String classroomName;

    /**
     * 讲座
     */
    private Long lectureId;
    private String lectureName;

    /**
     * 讲师
     */
    private Long teacherId;
    private String teacherName;

    /**
     * 预约描述
     */
    private String appointmentDescription;

    /**
     * 预约状态
     */
    private String status;

    /**
     * 审核人
     */
    private Long auditUserId;
    private String auditUserName;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;

    /**
     * 审核描述
     */
    private String auditDescription;

    /**
     * 备注
     */
    private String remark;

    /**
     * 预约人
     */
    private Long userId;
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param appointment Appointment实体对象
     * @return AppointmentVo
     */
    public static AppointmentVo objToVo(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        AppointmentVo appointmentVo = new AppointmentVo();
        BeanUtils.copyProperties(appointment, appointmentVo);
        return appointmentVo;
    }
}
