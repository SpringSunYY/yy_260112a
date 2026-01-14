package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 讲座审核对象 tb_lecture_audit
 *
 * @author YY
 * @date 2026-01-14
 */
@TableName("tb_lecture_audit")
@Data
public class LectureAudit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 教室 */
    @Excel(name = "教室")
    private Long classroomId;

    /** 讲座 */
    @Excel(name = "讲座")
    private Long lectureId;

    /** 讲师 */
    @Excel(name = "讲师")
    private Long teacherId;

    /** 人数 */
    @Excel(name = "人数")
    private Long peopleNumberLimit;

    /** 状态 */
    @Excel(name = "状态", dictType = "audit_status")
    private String status;

    /** 审核描述 */
    @Excel(name = "审核描述")
    private String description;

    /** 审核人 */
    @Excel(name = "审核人")
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
