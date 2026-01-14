package com.lz.manage.model.dto.sign;

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
import com.lz.manage.model.domain.Sign;
/**
 * 签到信息Query对象 tb_sign
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class SignQuery implements Serializable
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
     * @param signQuery 查询对象
     * @return Sign
     */
    public static Sign queryToObj(SignQuery signQuery) {
        if (signQuery == null) {
            return null;
        }
        Sign sign = new Sign();
        BeanUtils.copyProperties(signQuery, sign);
        return sign;
    }
}
