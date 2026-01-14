package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 审核状态 枚举
 */
@Getter
public enum AuditStatusEnum {

    /**
     * 同意
     */
    AUDIT_STATUS_1("1", "同意"),

    /**
     * 拒绝
     */
    AUDIT_STATUS_2("2", "拒绝");

    private static final Map<String, AuditStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (AuditStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    AuditStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 数据库存储值
     * @return 对应枚举（Optional）
     */
    public static Optional<AuditStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
