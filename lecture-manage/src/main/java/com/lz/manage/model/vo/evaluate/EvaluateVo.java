package com.lz.manage.model.vo.evaluate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Evaluate;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价信息Vo对象 tb_evaluate
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class EvaluateVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 教室
     */
    private Long classroomId;
    private String classroomName;

    /**
     * 讲座
     */
    private Long lectureId;
    private String lectureName;

    /**
     * 讲师
     */
    private Long teacherId;
    private String teacherName;

    /**
     * 标题
     */
    private String title;

    /**
     * 评分
     */
    private Long score;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long userId;
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param evaluate Evaluate实体对象
     * @return EvaluateVo
     */
    public static EvaluateVo objToVo(Evaluate evaluate) {
        if (evaluate == null) {
            return null;
        }
        EvaluateVo evaluateVo = new EvaluateVo();
        BeanUtils.copyProperties(evaluate, evaluateVo);
        return evaluateVo;
    }
}
