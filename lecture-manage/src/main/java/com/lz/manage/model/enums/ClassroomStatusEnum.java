package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 教室状态 枚举
 */
@Getter
public enum ClassroomStatusEnum {

    /**
     * 正常
     */
    CLASSROOM_STATUS_1("1", "正常"),

    /**
     * 异常
     */
    CLASSROOM_STATUS_2("2", "异常");

    private static final Map<String, ClassroomStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ClassroomStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    ClassroomStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 数据库存储值
     * @return 对应枚举（Optional）
     */
    public static Optional<ClassroomStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
