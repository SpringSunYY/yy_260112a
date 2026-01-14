package com.lz.manage.model.dto.lectureAudit;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.LectureAudit;
/**
 * 讲座审核Query对象 tb_lecture_audit
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class LectureAuditQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 教室 */
    private Long classroomId;

    /** 讲座 */
    private Long lectureId;

    /** 讲师 */
    private Long teacherId;

    /** 状态 */
    private String status;

    /** 审核人 */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param lectureAuditQuery 查询对象
     * @return LectureAudit
     */
    public static LectureAudit queryToObj(LectureAuditQuery lectureAuditQuery) {
        if (lectureAuditQuery == null) {
            return null;
        }
        LectureAudit lectureAudit = new LectureAudit();
        BeanUtils.copyProperties(lectureAuditQuery, lectureAudit);
        return lectureAudit;
    }
}
