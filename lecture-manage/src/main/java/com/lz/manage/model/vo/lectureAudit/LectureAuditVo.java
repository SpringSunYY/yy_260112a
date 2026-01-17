package com.lz.manage.model.vo.lectureAudit;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.LectureAudit;
/**
 * 讲座审核Vo对象 tb_lecture_audit
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class LectureAuditVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 教室 */
    private Long classroomId;
    private String classroomName;

    /** 讲座 */
    private Long lectureId;
    private String lectureName;

    /** 讲师 */
    private Long teacherId;
    private String teacherName;

    /** 人数 */
    private Long peopleNumberLimit;

    /** 状态 */
    private String status;

    /** 审核描述 */
    private String description;

    /** 审核人 */
    private Long userId;
    private String userName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param lectureAudit LectureAudit实体对象
     * @return LectureAuditVo
     */
    public static LectureAuditVo objToVo(LectureAudit lectureAudit) {
        if (lectureAudit == null) {
            return null;
        }
        LectureAuditVo lectureAuditVo = new LectureAuditVo();
        BeanUtils.copyProperties(lectureAudit, lectureAuditVo);
        return lectureAuditVo;
    }
}
