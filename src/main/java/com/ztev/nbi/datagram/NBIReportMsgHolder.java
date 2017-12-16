package com.ztev.nbi.datagram;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ztev.nbi.util.NBIUtils;

import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/22.
 *
 * @copyright by ztev
 */
@JsonPropertyOrder({"PlatformID","MsgType","Data","TimeStamp","Sig"})
public class NBIReportMsgHolder
{
    @JsonProperty("PlatformID")
    public String getPlatformID() {
        return PlatformID;
    }

    public void setPlatformID(String platformID) {
        PlatformID = platformID;
    }
    @JsonProperty("MsgType")
    public int getMsgType() {
        return MsgType;
    }

    public void setMsgType(int msgType) {
        MsgType = msgType;
    }
    @JsonProperty("Data")
    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
    @JsonProperty("TimeStamp")
    @JsonFormat(pattern= NBIUtils.DATEFMT,timezone="GMT+8")
    public Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        TimeStamp = timeStamp;
    }
    @JsonProperty("Sig")
    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    private String PlatformID;
    private int MsgType;
    private String Data;

    private Date TimeStamp;
    private String sig;
}
