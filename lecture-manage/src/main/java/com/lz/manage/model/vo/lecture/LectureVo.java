package com.lz.manage.model.vo.lecture;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Lecture;
/**
 * 讲座信息Vo对象 tb_lecture
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class LectureVo implements Serializable
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

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param lecture Lecture实体对象
     * @return LectureVo
     */
    public static LectureVo objToVo(Lecture lecture) {
        if (lecture == null) {
            return null;
        }
        LectureVo lectureVo = new LectureVo();
        BeanUtils.copyProperties(lecture, lectureVo);
        return lectureVo;
    }
}
