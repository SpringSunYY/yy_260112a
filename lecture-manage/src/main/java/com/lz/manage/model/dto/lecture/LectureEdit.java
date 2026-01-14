package com.lz.manage.model.dto.lecture;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Lecture;
/**
 * 讲座信息Vo对象 tb_lecture
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class LectureEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 教室 */
    private Long classroomId;

    /** 名称 */
    private String name;

    /** 讲师 */
    private Long teacherId;

    /** 人数 */
    private Long peopleNumberLimit;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lectureStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lectureEndTime;

    /** 参加人数 */
    private Long peopleJoinNumber;

    /** 签到人数 */
    private Long peopleSignNumber;

    /** 状态 */
    private String status;

    /** 讲座描述 */
    private String description;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param lectureEdit 编辑对象
     * @return Lecture
     */
    public static Lecture editToObj(LectureEdit lectureEdit) {
        if (lectureEdit == null) {
            return null;
        }
        Lecture lecture = new Lecture();
        BeanUtils.copyProperties(lectureEdit, lecture);
        return lecture;
    }
}
