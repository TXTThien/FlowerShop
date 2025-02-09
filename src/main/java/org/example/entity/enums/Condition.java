package org.example.entity.enums;

public enum Condition {
    Pending,
    Processing,
    Prepare,
    In_Transit,
    Shipper_Delivering,
    First_Attempt_Failed,
    Second_Attempt_Failed,
    Third_Attempt_Failed,
    Cancelled,
    Delivered_Successfully,
    Return_to_shop,
    Refund,
    Refund_is_Processing,
    Cancel_is_Processing;
    @Override
    public String toString() {
        return name().replace("_", " ");
    }
}
