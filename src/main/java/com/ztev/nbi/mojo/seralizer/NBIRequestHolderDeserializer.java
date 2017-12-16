package com.ztev.nbi.mojo.seralizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.ztev.nbi.datagram.NBIRequestHolder;
import com.ztev.nbi.util.NBIUtils;

import java.io.IOException;
import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/22.
 *
 * @copyright by ztev
 */
public class NBIRequestHolderDeserializer  extends JsonDeserializer<NBIRequestHolder> {


    @Override
    public NBIRequestHolder deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        String pid = node.get("PlatformID")!= null ?node.get("PlatformID").textValue():null;
        Date timestamp = node.get("TimeStamp")!=null ? NBIUtils.getDateFromString(NBIUtils.DATEFMT,node.get("TimeStamp").textValue()):
                            null;
        String data = node.get("Data")!= null ?node.get("Data").textValue():null;

        String signature = node.get("signature")!= null ?node.get("signature").textValue():null;

       return NBIRequestHolder.builder().Data(data).TimeStamp(timestamp).PlatformID(pid).Sig(signature).build();
    }
}
