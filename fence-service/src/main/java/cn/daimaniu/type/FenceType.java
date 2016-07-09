package cn.daimaniu.type;

/**
 * Author :chenqisheng
 * Date   :2016-07-09 19:32.
 * <p>
 * 围栏类别
 */
public enum FenceType {
    CIRCLE(1), POLYGON(2);
    private int value;

    FenceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
