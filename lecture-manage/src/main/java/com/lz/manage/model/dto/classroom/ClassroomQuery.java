package com.lz.manage.model.dto.classroom;

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
import com.lz.manage.model.domain.Classroom;
/**
 * 教室信息Query对象 tb_classroom
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class ClassroomQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 名称 */
    private String name;

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
     * @param classroomQuery 查询对象
     * @return Classroom
     */
    public static Classroom queryToObj(ClassroomQuery classroomQuery) {
        if (classroomQuery == null) {
            return null;
        }
        Classroom classroom = new Classroom();
        BeanUtils.copyProperties(classroomQuery, classroom);
        return classroom;
    }
}
