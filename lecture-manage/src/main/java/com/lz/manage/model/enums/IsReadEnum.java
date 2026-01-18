package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 是否已读 状态枚举
 */
@Getter
public enum IsReadEnum {

    /**
     * 已读
     */
    IS_READ_1("1", "已读"),

    /**
     * 未读
     */
    IS_READ_2("2", "未读");

    private static final Map<String, IsReadEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (IsReadEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    IsReadEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 数据库存储值
     * @return 对应枚举（Optional）
     */
    public static Optional<IsReadEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
