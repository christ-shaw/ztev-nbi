package com.ztev.nbi.mojo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes(
        {@JsonSubTypes.Type(name="pid",value=NBIParkLotId.class),
        @JsonSubTypes.Type(name="queryCond",value=NBIQueyLogCondition.class)})
abstract public class NBIQuery {
}
