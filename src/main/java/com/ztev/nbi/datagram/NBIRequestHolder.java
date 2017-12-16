package com.ztev.nbi.datagram;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ztev.nbi.mojo.seralizer.CustomDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
/*
PlatformID
Data
TimeStamp
Sig
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NBIRequestHolder
{
    private  String PlatformID;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date TimeStamp;
    private String Sig;
    private String Data;
}
