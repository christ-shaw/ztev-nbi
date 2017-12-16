package com.ztev.nbi.mojo.seralizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.ztev.nbi.mojo.NBIQueyLogCondition;
import com.ztev.nbi.util.NBIUtils;

import java.io.IOException;
import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
public class NBICondDeserializer extends JsonDeserializer<NBIQueyLogCondition> {
    @Override
    public NBIQueyLogCondition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        Date beginTime = node.get("beginTimeForSearch") != null ?
                NBIUtils.getDateFromString(NBIUtils.DATEFMT,node.get("beginTimeForSearch").textValue()): new Date(0);

        Date endTime = node.get("endTimeForSearch") != null ?
                NBIUtils.getDateFromString(NBIUtils.DATEFMT,node.get("endTimeForSearch").textValue()): new Date();

        String  tid = node.get("transactionId") != null? node.get("transactionId").textValue():null;

        String sid = node.get("stationaryId") != null ? node.get("stationaryId").textValue(): null;

        String licencePlate = node.get("licencePlate") != null ? node.get("licencePlate").textValue(): null;


        return   NBIQueyLogCondition.builder().beginTime(beginTime).endTime(endTime)
                                       .transactionId(tid).stationaryId(sid).licensePlate(licencePlate).build();
    }
}
