package com.ztev.nbi.mojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ztev.nbi.util.NBIUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/23.
 *
 * @copyright by ztev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NBICarLeaveMsg
{
    private String transactionId;
    private String stationaryId;
    private int userType;
    private String licencePlate;
    private String exitName;
    @JsonFormat(pattern= NBIUtils.DATEFMT,timezone="GMT+8")
    private Date leaveTime;
}
