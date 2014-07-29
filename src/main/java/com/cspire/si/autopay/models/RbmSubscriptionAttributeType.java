package com.cspire.si.autopay.models;

public enum RbmSubscriptionAttributeType {

    SUBSCRIPTION_TYPE(1),
    SALES_CHANNEL(2),
    SERVICE_LOCATION(3),
    CONCATENATED_SUBSCRIPTION_ATTRIBUTES_1_4(4),
    BASE_PLAN_PRICE_PLAN_NAME(5),
    CONCATENATED_SUBSCRIPTION_ATTRIBUTES_5_6(6),
    SUBSCRIPTION_STATUS(7),
    ACCESS_SERVICE_TYPE(8),
    ACTIVATION_LOCATION(9),
    MOBILE_NUMBER(10),
    MIN(11),
    DATE_OF_BIRTH(12),
    FIFTY_FIFTY_FLAG(13),
    SERIAL_NUMBER(14),
    IMSI(15),
    ROAM_IMSI(16), 
    FIFTY_FIFTY_VIOLATION_MONTHS(17),
    STATE(18),
    SUBSCRIPTION_ID(19),
    CSN(20);
    
    private int value;

    private RbmSubscriptionAttributeType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static RbmSubscriptionAttributeType fromValue(int value) {
        for (RbmSubscriptionAttributeType enumValue : RbmSubscriptionAttributeType.values()) {
            if (enumValue.value() == value) {
                return enumValue;
            }
        }

        return null;
    }
}