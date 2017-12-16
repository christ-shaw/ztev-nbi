package com.ztev.nbi.mojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ztev.nbi.util.NBIUtils;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
@Data
@Builder
/*
transactionId
stationaryId
userType
licenceColor
licencePlate
entranceName
enterTime
exitName
leaveTime
totalAmount
paidstate
 */
public class NBIParkingLog
{
    @JsonProperty("transactionId")
    String transactionId;
    @JsonProperty("stationaryId")
    String stationaryId;
    @JsonProperty("userType")
    int userType;
    @JsonProperty("licenceColor")
    String licenceColor;
    @JsonProperty("licencePlate")
    String licencePlate;
    @JsonProperty("enterName")
    String enterName;
    @JsonProperty("enterTime")
    @JsonFormat(pattern= NBIUtils.DATEFMT,timezone="GMT+8")
    Date enterTime;
    @JsonProperty("exitName")
    String exitName;
    @JsonFormat(pattern= NBIUtils.DATEFMT,timezone="GMT+8")
    @JsonProperty("leaveTime")
    Date  leaveTime;
    @JsonProperty("totalAmount")
    Double totalAmount;
    @JsonProperty("paidState")
    int paidState;
}
