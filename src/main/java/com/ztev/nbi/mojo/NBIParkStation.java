package com.ztev.nbi.mojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ztev.nbi.util.NBIUtils;
import lombok.Builder;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
/*
id
displayName
location
positionX
positionY
createTime
phone
parkSpaces
 */
@Data
@Builder
public class NBIParkStation
{
    @JsonProperty("id")
    private String id;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("positionX")
    private Double positionX;
    @JsonProperty("positionY")
    private Double positionY;
    @JsonFormat(pattern= NBIUtils.DATEFMT,timezone="GMT+8")
    @JsonProperty("createTime")
    private Date createTime;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("parkSpaces")
    private int parkSpaces;
}
