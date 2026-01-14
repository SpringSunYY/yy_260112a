package com.lz.manage.model.vo.sign;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Sign;
/**
 * 签到信息Vo对象 tb_sign
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class SignVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 教室 */
    private Long classroomId;

    /** 讲座 */
    private Long lectureId;

    /** 讲师 */
    private Long teacherId;

    /** 签到人 */
    private String name;

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
     * @param sign Sign实体对象
     * @return SignVo
     */
    public static SignVo objToVo(Sign sign) {
        if (sign == null) {
            return null;
        }
        SignVo signVo = new SignVo();
        BeanUtils.copyProperties(sign, signVo);
        return signVo;
    }
}
