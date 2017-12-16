package com.ztev.nbi.mojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ztev.nbi.util.NBIUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/12/4.
 *
 * @copyright by ztev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NBIItemizedBill
{
    /**
     * 停车交易号
     */
    private String transactionID;

    /**
     * 停车场OID
     */
    private String parkStationOID;

    /**
     * 车牌号
     */
    private String licencePlate;

    /**
     * 本次停车应收总金额
     */
    private double totalAmtReceivable;

    /**
     * 停车开始时间
     */
    @JsonFormat(pattern= NBIUtils.DATEFMT,timezone="GMT+8")
    private Date plBeginTime = new Date();

    /**
     * 停车时长
     */
    private String plDuration;


}
