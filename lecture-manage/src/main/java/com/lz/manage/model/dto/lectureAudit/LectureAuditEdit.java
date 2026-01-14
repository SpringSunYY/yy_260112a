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
public class LectureAuditEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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
     * @param lectureAuditEdit 编辑对象
     * @return LectureAudit
     */
    public static LectureAudit editToObj(LectureAuditEdit lectureAuditEdit) {
        if (lectureAuditEdit == null) {
            return null;
        }
        LectureAudit lectureAudit = new LectureAudit();
        BeanUtils.copyProperties(lectureAuditEdit, lectureAudit);
        return lectureAudit;
    }
}
