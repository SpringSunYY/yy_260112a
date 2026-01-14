package com.lz.manage.model.dto.lecture;

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
import com.lz.manage.model.domain.Lecture;
/**
 * 讲座信息Query对象 tb_lecture
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class LectureQuery implements Serializable
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

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lectureStartTime;

    /** 状态 */
    private String status;

    /** 创建人 */
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
     * @param lectureQuery 查询对象
     * @return Lecture
     */
    public static Lecture queryToObj(LectureQuery lectureQuery) {
        if (lectureQuery == null) {
            return null;
        }
        Lecture lecture = new Lecture();
        BeanUtils.copyProperties(lectureQuery, lecture);
        return lecture;
    }
}
