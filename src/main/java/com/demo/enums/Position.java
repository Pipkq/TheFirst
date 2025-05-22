package com.demo.enums;

public enum Position {
    MANAGER("经理"),
    RECEPTIONIST("前台接待"),
    HOUSEKEEPING("客房服务"),
    MAINTENANCE("维修人员"),
    SECURITY("安保人员"),
    CHEF("厨师"),
    WAITER("服务员");

    private final String displayName;

    Position(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}