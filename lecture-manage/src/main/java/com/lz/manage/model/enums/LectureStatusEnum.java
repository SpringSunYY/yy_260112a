package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 讲座状态 枚举
 */
@Getter
public enum LectureStatusEnum {

    /**
     * 待审核
     */
    LECTURE_STATUS_1("1", "待审核"),

    /**
     * 待开始
     */
    LECTURE_STATUS_2("2", "待开始"),

    /**
     * 进行中
     */
    LECTURE_STATUS_3("3", "进行中"),

    /**
     * 已过期
     */
    LECTURE_STATUS_4("4", "已过期"),

    /**
     * 拒绝
     */
    LECTURE_STATUS_5("5", "拒绝");

    private static final Map<String, LectureStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (LectureStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    LectureStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 数据库存储值
     * @return 对应枚举（Optional）
     */
    public static Optional<LectureStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
