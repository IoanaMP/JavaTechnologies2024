/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.uaic.vrp.Utils.Enums;

/**
 *
 * @author ioana
 */
public enum OrderStatusEnum {
    Pending(1),
    Processing(2),
    ReadyforDelivery(3),
    Shipped(4),
    Completed(5),
    Cancelled(6);

    private final int value;

    OrderStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderStatusEnum fromValue(int value) {
        for (OrderStatusEnum status : OrderStatusEnum.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}