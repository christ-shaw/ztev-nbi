package com.ztev.nbi.jms;

/**
 * 停车日志消息队列监听
 *
 * @author leo
 * @ClassName: ParkingLogListener
 * @date 2016年9月27日
 */

public enum MsgType
{
    ENTER(1),
    LICENSEUPDATE(2),
    LEAVE(3),
    BILLING(4);

    private int stage;
    MsgType(int i) {
        this.stage =i;
    }

    public int getStage() {
        return stage;
    }
};
