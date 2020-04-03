package com.example.security.jwt.system.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author PD
 */

public enum UserStatusEnum {
    NORMAL(0, "正常"),
    LOCKED(1, "锁定"),
    CANCELED(2, "注销");


    private int code;

    private String message;

    UserStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 通过码获取枚举项
     *
     * @param code
     * @return
     */
    @JsonCreator
    public static UserStatusEnum getByCode(int code) {
        for (UserStatusEnum item : UserStatusEnum.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return NORMAL;
    }
}
