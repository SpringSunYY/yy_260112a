package com.lz.manage.model.dto.classroom;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Classroom;
/**
 * 教室信息Vo对象 tb_classroom
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class ClassroomInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 名称 */
    private String name;

    /** 状态 */
    private String status;

    /** 图片 */
    private String image;

    /** 描述 */
    private String description;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param classroomInsert 插入对象
     * @return ClassroomInsert
     */
    public static Classroom insertToObj(ClassroomInsert classroomInsert) {
        if (classroomInsert == null) {
            return null;
        }
        Classroom classroom = new Classroom();
        BeanUtils.copyProperties(classroomInsert, classroom);
        return classroom;
    }
}
