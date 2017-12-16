package com.ztev.nbi.mojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ztev.nbi.util.NBIUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/22.
 *
 * @copyright by ztev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NBICarEntryMsg {
    private String transactionId;
    private String stationaryId;
    private int userType;
    private String licenceColor;
    private String licencePlate;
    private String entranceName;
    @JsonFormat(pattern= NBIUtils.DATEFMT,timezone="GMT+8")
    private Date enterTime;
}
