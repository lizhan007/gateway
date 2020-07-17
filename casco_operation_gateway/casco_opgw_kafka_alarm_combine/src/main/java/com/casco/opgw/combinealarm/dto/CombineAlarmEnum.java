package com.casco.opgw.combinealarm.dto;

public enum CombineAlarmEnum implements CommonAlarmEnum {

    // 报警：防滑抑制
    SLIDE_SUPPRESSION((float)5000001, "T9",
            "结合部（车辆-信号）", "车轴", (float)2, (float)32401),
    // 报警：防滑激活
    SLIDE_ACTIVE((float)5000002, "T9",
            "结合部（车辆-信号）", "车架", (float)2, (float)32401),
    // 报警：轮径差故障
    SLIDE_WHEEL_DIAMETER_DIFF((float)5000003, "T9",
            "结合部（车辆-信号）", "牵引", (float)2, (float)31701),
    // 报警：车辆打滑
    SLIDE_TRAIN((float)5000004, "T9",
            "结合部（车辆-信号）", "车辆", (float)2, (float)500),
    // 报警：信号和车辆打滑信号不一致
    SLIDE_SIG_TRAIN_DIFF((float)5000005, "T9",
            "结合部（车辆-信号）", "车辆", (float)2, (float)500),
    ;

    private CombineAlarmEnum(Float armCode, String armDbm,
                            String armSource, String armEquType, Float armLevel, Float armEquTypeCode) {
        this.armCode = armCode;
        this.armDbm = armDbm;
        this.armSource = armSource;
        this.armEquType = armEquType;
        this.armLevel = armLevel;
        this.armEquTypeCode = armEquTypeCode;
    }

    private Float armCode;
    private String armDbm;
    private String armSource;
    private String armEquType;
    private Float armLevel;
    private Float armEquTypeCode;


    @Override
    public String getArmDbm() {
        return this.armDbm;
    }

    @Override
    public Float getArmCode() {
        return this.armCode;
    }

    @Override
    public String getArmSource() {
        return this.armSource;
    }

    @Override
    public String getArmEquType() {
        return this.armEquType;
    }

    @Override
    public Float getArmLevel() {
        return this.armLevel;
    }

    @Override
    public Float getArmEquTypeCode() {
        return this.armEquTypeCode;
    }
}
