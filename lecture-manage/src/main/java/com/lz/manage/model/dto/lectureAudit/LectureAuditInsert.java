package com.lz.manage.model.dto.lectureAudit;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.LectureAudit;
/**
 * 讲座审核Vo对象 tb_lecture_audit
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class LectureAuditInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 讲座 */
    private Long lectureId;

    /** 状态 */
    private String status;

    /** 审核描述 */
    private String description;

    /** 审核人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param lectureAuditInsert 插入对象
     * @return LectureAuditInsert
     */
    public static LectureAudit insertToObj(LectureAuditInsert lectureAuditInsert) {
        if (lectureAuditInsert == null) {
            return null;
        }
        LectureAudit lectureAudit = new LectureAudit();
        BeanUtils.copyProperties(lectureAuditInsert, lectureAudit);
        return lectureAudit;
    }
}
