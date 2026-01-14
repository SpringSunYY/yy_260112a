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
public class ClassroomEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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
     * @param classroomEdit 编辑对象
     * @return Classroom
     */
    public static Classroom editToObj(ClassroomEdit classroomEdit) {
        if (classroomEdit == null) {
            return null;
        }
        Classroom classroom = new Classroom();
        BeanUtils.copyProperties(classroomEdit, classroom);
        return classroom;
    }
}
