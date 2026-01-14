package com.lz.manage.model.vo.classroom;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Classroom;
/**
 * 教室信息Vo对象 tb_classroom
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class ClassroomVo implements Serializable
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

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param classroom Classroom实体对象
     * @return ClassroomVo
     */
    public static ClassroomVo objToVo(Classroom classroom) {
        if (classroom == null) {
            return null;
        }
        ClassroomVo classroomVo = new ClassroomVo();
        BeanUtils.copyProperties(classroom, classroomVo);
        return classroomVo;
    }
}
