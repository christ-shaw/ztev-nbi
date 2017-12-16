package com.ztev.nbi.mojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ztev.nbi.mojo.seralizer.NBICondDeserializer;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
/*
transactionId
stationaryId
licencePlate
beginTimeForSearch
endTimeForSearch
 */
@Data
@Builder
@JsonDeserialize(using = NBICondDeserializer.class)
public class NBIQueyLogCondition
{
    String transactionId;
    String stationaryId;
    String licensePlate;
    Date beginTime;
    Date endTime;
}
