package com.ztev.nbi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztev.nbi.datagram.NBIReportMsgHolder;
import com.ztev.nbi.datagram.NBIResponseHolder;
import com.ztev.nbi.security.NBIOperInfo;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
public class NBIUtils {
    private static  Logger logger = LoggerFactory.getLogger(NBIUtils.class.getName());
    public static final String DATEFMT = "yyyy-MM-dd HH:mm:ss";
    public static final String SECRETKEY = "NBI@ZTEV";

    public static String getStringFormatForDate(String fmt, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        return format.format(date);
    }

    public static Date getDateFromString(String fmt, String value) {
        if (value  == null)
        {
            logger.error("null string ");
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        try {
            return format.parse(value);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    public static ResponseEntity<NBIResponseHolder> send(int errorCode, String errReason) {
        NBIResponseHolder holder = createErrorResponseHolder(errorCode, errReason);
        HttpStatus status;
        switch (errorCode) {
            case 1:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 2:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            case 3:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(holder, status);
    }

    public static NBIResponseHolder createErrorResponseHolder(int errorCode, String errReason) {
        Date now = new Date();
        String sig = makeSign(SECRETKEY, String.valueOf(errorCode), errReason, NBIUtils.getStringFormatForDate(NBIUtils.DATEFMT, now));
        NBIResponseHolder holder = new NBIResponseHolder();
        holder.setRet(errorCode);
        holder.setRetMsg(errReason);
        holder.setTimeStamp(new Date());
        holder.setSig(sig);
        holder.setData(null);
        return holder;
    }


    public static ResponseEntity<NBIResponseHolder> createSuccessResponse(String data) {
        NBIResponseHolder holder = createSuccessResponder(data);
        return new ResponseEntity<NBIResponseHolder>(holder, HttpStatus.OK);
    }

    public static NBIResponseHolder createSuccessResponder(String data) {
        Date now = new Date();
        String sig = makeSign(SECRETKEY, String.valueOf(0), data, NBIUtils.getStringFormatForDate(NBIUtils.DATEFMT, now));
        NBIResponseHolder holder = new NBIResponseHolder();
        holder.setRet(0);
        holder.setRetMsg("");
        holder.setTimeStamp(new Date());
        holder.setSig(sig);
        holder.setData(data);
        return holder;

    }

    public static NBIReportMsgHolder createReportHolder(String data, int msgType) {
        Date now = new Date();
        String platformID = "ztev-123";
        String sig = makeSign(SECRETKEY, platformID, String.valueOf(msgType), data, getStringFormatForDate(DATEFMT, now));
         NBIReportMsgHolder holder = new NBIReportMsgHolder();
        holder.setData(data);
        holder.setMsgType(msgType);
        holder.setPlatformID(platformID);
        holder.setTimeStamp(now);
        holder.setSig(sig);
        return holder;
    }

    public static String makeSign(String SigSecret, String... args) {
        if (SigSecret.isEmpty() || args == null || args.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg);
        }
        return HmacUtils.hmacMd5Hex(SigSecret, sb.toString()).toUpperCase();
    }

    public static void main(String[] args) throws JsonProcessingException {

        String pid = "123";
        String date = "2017-11-12 11:21:00";
        String data = "{\"stationaryId\" :\"ztev.plstation=0x123245567889993333\",\"beginTimeForSearch\":\"2017-09-02 10:17:17\",\"endTimeForSearch\":\"2017-09-02 14:17:17\"}";


        String x = makeSign(NBIUtils.SECRETKEY, pid, data, date);

        //  System.out.println(x);

        String data2 = "{\"parkingLotId\":\"ztev.plstation=0x020211090700000001\"}";
        String x1 = makeSign(NBIUtils.SECRETKEY, pid, data2, "2017-11-12 11:21:00");
        //  System.out.println(x1);


        String x3 = makeSign("vp2BDUGdfUDPIRXs", "10002", "2017-11-28 18:14:10");
        System.out.println(x3);
    }
}
