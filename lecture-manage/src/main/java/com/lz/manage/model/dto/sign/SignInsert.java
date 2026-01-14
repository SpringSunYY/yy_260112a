package com.lz.manage.model.dto.sign;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Sign;
/**
 * 签到信息Vo对象 tb_sign
 *
 * @author YY
 * @date 2026-01-14
 */
@Data
public class SignInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 讲座 */
    private Long lectureId;

    /** 签到人 */
    private String name;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param signInsert 插入对象
     * @return SignInsert
     */
    public static Sign insertToObj(SignInsert signInsert) {
        if (signInsert == null) {
            return null;
        }
        Sign sign = new Sign();
        BeanUtils.copyProperties(signInsert, sign);
        return sign;
    }
}
