package com.lz.manage.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 预约信息对象 tb_appointment
 *
 * @author YY
 * @date 2026-01-14
 */
@TableName("tb_appointment")
@Data
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 教室
     */
    @Excel(name = "教室", type = Excel.Type.IMPORT)
    private Long classroomId;
    @TableField(exist = false)
    @Excel(name = "教室", type = Excel.Type.EXPORT)
    private String classroomName;
    /**
     * 讲座
     */
    @Excel(name = "讲座", type = Excel.Type.IMPORT)
    private Long lectureId;
    @TableField(exist = false)
    @Excel(name = "讲座", type = Excel.Type.EXPORT)
    private String lectureName;

    /**
     * 讲师
     */
    @Excel(name = "讲师", type = Excel.Type.IMPORT)
    private Long teacherId;
    @TableField(exist = false)
    @Excel(name = "讲师", type = Excel.Type.EXPORT)
    private String teacherName;

    /**
     * 预约描述
     */
    @Excel(name = "预约描述")
    private String appointmentDescription;

    /**
     * 预约状态
     */
    @Excel(name = "预约状态", dictType = "appointment_status")
    private String status;

    /**
     * 审核人
     */
    @Excel(name = "审核人", type = Excel.Type.IMPORT)
    private Long auditUserId;
    @TableField(exist = false)
    @Excel(name = "审核人", type = Excel.Type.EXPORT)
    private String auditUserName;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /**
     * 审核描述
     */
    @Excel(name = "审核描述")
    private String auditDescription;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 预约人
     */
    @Excel(name = "预约人", type = Excel.Type.IMPORT)
    private Long userId;
    @TableField(exist = false)
    @Excel(name = "预约人", type = Excel.Type.EXPORT)
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
