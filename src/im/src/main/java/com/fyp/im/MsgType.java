package com.fyp.im;

public enum MsgType {
    ping((byte)3),
    clientToClient((byte) 1),
    clientToGroup((byte)2);

    MsgType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    private byte value;

    public static MsgType valueOf(byte value) {
        for (MsgType type : MsgType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("value "+ value+" is not a MsgType");
    }
}
