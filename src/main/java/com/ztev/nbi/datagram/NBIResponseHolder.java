package com.ztev.nbi.datagram;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ztev.nbi.util.NBIUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonMethod;

import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
/*
Ret
RetMsg
Data
TimeStamp
Sig
 */
@JsonPropertyOrder({"Ret","RetMsg","Data","TimeStamp","Sig"})

public class NBIResponseHolder
{
    @JsonProperty("Ret")
    public int getRet() {
        return Ret;
    }

    public void setRet(int ret) {
        Ret = ret;
    }
    @JsonProperty("RetMsg")
    public String getRetMsg() {
        return RetMsg;
    }

    public void setRetMsg(String retMsg) {
        RetMsg = retMsg;
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


    private int Ret;

    private String RetMsg;

    private String Data;


    private Date TimeStamp;

    private String sig;


}
