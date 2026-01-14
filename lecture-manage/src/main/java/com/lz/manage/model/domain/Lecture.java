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
 * 讲座信息对象 tb_lecture
 *
 * @author YY
 * @date 2026-01-14
 */
@TableName("tb_lecture")
@Data
public class Lecture implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 教室 */
    @Excel(name = "教室")
    private Long classroomId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 讲师 */
    @Excel(name = "讲师")
    private Long teacherId;

    /** 人数 */
    @Excel(name = "人数")
    private Long peopleNumberLimit;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lectureStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lectureEndTime;

    /** 参加人数 */
    @Excel(name = "参加人数")
    private Long peopleJoinNumber;

    /** 签到人数 */
    @Excel(name = "签到人数")
    private Long peopleSignNumber;

    /** 状态 */
    @Excel(name = "状态", dictType = "lecture_status")
    private String status;

    /** 讲座描述 */
    @Excel(name = "讲座描述")
    private String description;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 创建人 */
    @Excel(name = "创建人")
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
