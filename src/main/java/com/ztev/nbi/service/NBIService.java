package com.ztev.nbi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.ums.ztev.chargingservice.parking.api.IParkingFeeService;
import com.zte.ums.ztev.chargingservice.parking.entity.ParkingInfoDetail;
import com.zte.ums.ztev.pl.parkinglog.IParkingLogService;
import com.zte.ums.ztev.pl.parkinglog.entity.ParkingLog;
import com.zte.ums.ztev.pl.parkinglog.entity.ParkingLogCondition;
import com.ztev.deductfee.parking.api.IParkingDeductFeeService;
import com.ztev.device.api.IParkStationService;
import com.ztev.device.entity.ParkStation;
import com.ztev.nbi.datagram.NBIRequestHolder;
import com.ztev.nbi.datagram.NBIResponseHolder;
import com.ztev.nbi.exception.SignatureIncompltedException;
import com.ztev.nbi.mojo.*;
import com.ztev.nbi.security.RegistryInfo;
import com.ztev.nbi.security.repository.OperatorRepository;
import com.ztev.nbi.util.NBIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
@RestController
@RequestMapping("/api")
public class NBIService
{
    private static final Logger logger = LoggerFactory.getLogger(NBIService.class.getName());

    @Autowired
    IParkStationService parkService;

    @Autowired
    IParkingLogService parkingLogService;

    @Autowired
    IParkingDeductFeeService feeService;

    @Autowired
    IParkingFeeService iParkingFeeService;

    @Autowired
   OperatorRepository opRepository;

    public void preCheck(NBIRequestHolder holder)throws SignatureIncompltedException
    {
        String signature = holder.getSig();
        String data = holder.getData();
        String pid = holder.getPlatformID();
        String date = NBIUtils.getStringFormatForDate(NBIUtils.DATEFMT,holder.getTimeStamp());

        RegistryInfo registryInfo = opRepository.findByOperatorId(pid);
        if (registryInfo == null)
        {
            logger.error("cannot found registry info for " +  pid);
            throw new SignatureIncompltedException();
        }

        logger.info("registryInfo.getSecretKey = "+ registryInfo.getSecretKey());
        logger.info("pid = "+ pid);
        logger.info("data = " + data);
        logger.info("date = " + date);
        String comparedSig =  NBIUtils.makeSign(registryInfo.getSecretKey(),pid,data,date);
        if(signature == null  || !signature.equalsIgnoreCase(comparedSig))
        {
            logger.error("expected signature " + signature);
            logger.error("actual signature " + comparedSig );
            throw new SignatureIncompltedException();
        }

    }


    @PostMapping(value = "/getAllParkingLots")
    public NBIResponseHolder getAllParkingLots(@RequestBody NBIRequestHolder holder)
    {
        logger.info("start invoking getAllParkingLots");
        preCheck(holder);
        NBIResponseHolder response = new NBIResponseHolder();
        List<ParkStation> parkStations = parkService.stationList();
        List<NBIParkStation> nbiParkStations = new LinkedList<NBIParkStation>();
                parkStations.stream().forEach( it -> {
                NBIParkStation nbiData = NBIParkStation.builder().id(it.getOid())
                        .displayName(it.getName()).positionX(it.getPositionX())
                        .positionY(it.getPositionY()).createTime(it.getCreateTime())
                        .phone(it.getPhone()).parkSpaces(it.getParkSpace()).build();
                    nbiParkStations.add(nbiData);
        });
        ObjectMapper mapper = new ObjectMapper();
        String data = null;
        try {
            data = mapper.writeValueAsString(nbiParkStations);
            response = NBIUtils.createSuccessResponder(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("end invoking getAllParkingLots");
       return  response;
    }

    @PostMapping(value = "/getAvailableSpaces")
    public NBIResponseHolder getAvailableSpaces(@RequestBody NBIRequestHolder requestHolder)
    {
        logger.info("start invoking getAvailableSpaces");
        preCheck(requestHolder);
        String data = requestHolder.getData();
        try {
            ObjectMapper mapper = new ObjectMapper();
            NBIParkLotId o = mapper.readerFor(NBIParkLotId.class).readValue(data);
            ParkStation parkStation = parkService.query(o.getParkingLotId());
            NBIAvailableSpaces spaces =  NBIAvailableSpaces.builder().availableSpaces(parkStation.getParkSpaceFree()).build();
             logger.info("end invoking getAvailableSpaces");
             return NBIUtils.createSuccessResponder(mapper.writeValueAsString(spaces));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @PostMapping(value = "/getAllParkingLogs")
    public NBIResponseHolder getAllParkingLogs(@RequestBody NBIRequestHolder requestHolder)
    {
        logger.info("start invoking getAllParkingLogs");
        preCheck(requestHolder);
        ObjectMapper mapper = new ObjectMapper();
        NBIQueyLogCondition nbiData = null;
        try {
            nbiData = mapper.readerFor(NBIQueyLogCondition.class).readValue(requestHolder.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<NBIParkingLog> result = new LinkedList<>();
        ParkingLogCondition condition = new ParkingLogCondition();
        if (nbiData.getStationaryId() != null)
        {
            condition.setStationOID(nbiData.getStationaryId());
        }
        if (nbiData.getTransactionId() != null)
        {

        }
        if (nbiData.getLicensePlate() != null)
        {
            condition.setKeyWord(nbiData.getLicensePlate());
        }
        if (nbiData.getBeginTime() != null)
        {
            condition.setStartDate(NBIUtils.getStringFormatForDate("yyyy-MM-dd HH:mm:SS",nbiData.getBeginTime()));
        }

        if (nbiData.getEndTime() != null)
        {
            condition.setEndDate(NBIUtils.getStringFormatForDate("yyyy-MM-dd HH:mm:SS",nbiData.getEndTime()));
        }

        List<ParkingLogCondition> conditions = new LinkedList<>();
        conditions.add(condition);
        try {
            List<ParkingLog> parkingLogs = parkingLogService.queryParkingLog(conditions);
            logger.info("get " + parkingLogs.size() + " logs from nms");
            parkingLogs.forEach(f ->
            {
                NBIParkingLog nbiParkingLog = NBIParkingLog.builder().enterName(f.getEntrance() != null?f.getEntrance() :"" )
                        .enterTime(NBIUtils.getDateFromString(NBIUtils.DATEFMT, f.getPlBeginTime()))
                        .exitName(f.getExport() != null?f.getExport() :"").leaveTime(f.getPlEndTime() != null ? NBIUtils.getDateFromString(NBIUtils.DATEFMT, f.getPlEndTime()) : null)
                        .licenceColor(f.getLicenceColor() != null?f.getLicenceColor() :"").licencePlate(f.getLicencePlate()!= null?f.getLicencePlate() :"")
                        .stationaryId(f.getPlStationOid()!= null?f.getPlStationOid() :"").transactionId(f.getTransactionId()!= null?f.getTransactionId() :"").build();
                result.add(nbiParkingLog);
            });

            logger.info("converted " + result.size() + "to NBI Logs");
            logger.info("end invoking getAvailableSpaces");
            return NBIUtils.createSuccessResponder(mapper.writeValueAsString(result));
        }  catch (JsonProcessingException e) {
            logger.error("error:{}" ,e);
            return  NBIUtils.createErrorResponseHolder(7,e.getMessage());
        } catch (Exception e) {
            logger.error("error:{}" ,e);
            return  NBIUtils.createErrorResponseHolder(7,e.getMessage());
        }

    }

    @RequestMapping(value = "/getFeeInfoByPlate")
    public NBIResponseHolder getFeeInfoByLicensePlate(@RequestBody NBIRequestHolder requestHolder)
    {
        logger.info("start invoking getFeeInfoByPlate");
        preCheck(requestHolder);
        String json = requestHolder.getData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            NBILicenseSelector o = mapper.readerFor(NBILicenseSelector.class).readValue(json);
            ParkingInfoDetail infoDetail = iParkingFeeService.queryParkingFeeDetailByLicencePlate(o.getLicensePlate());
            NBIItemizedBill bill;
            if (infoDetail == null)
            {
                logger.warn("empty result from nms");
                bill = new NBIItemizedBill();
            }
            else
            {
                logger.info("Receive license   " + infoDetail .getLicencePlate());
                logger.info("Receive getParkStationOID   " + infoDetail .getParkStationOID());
                logger.info("Receive getPlBeginTime   " + infoDetail .getPlBeginTime());
                logger.info("Receive getPlDuration   " + infoDetail .getPlDuration());
                logger.info("Receive getParkingFee   " + infoDetail .getParkingFee());


                bill = NBIItemizedBill.builder().transactionID(infoDetail.getTransID()).licencePlate(infoDetail.getLicencePlate())
                        .parkStationOID(infoDetail.getParkStationOID()).plBeginTime(NBIUtils.getDateFromString(NBIUtils.DATEFMT,infoDetail.getPlBeginTime()))
                        .plDuration(infoDetail.getPlDuration()).totalAmtReceivable(infoDetail.getParkingFee()).build();
            }

            logger.info("end up invoking getFeeInfoByPlate");
            return NBIUtils.createSuccessResponder(mapper.writeValueAsString(bill));

        } catch (IOException e) {
            logger.error("error:{}" ,e);
            return  NBIUtils.createErrorResponseHolder(7,e.getMessage());
        } catch (Exception e) {
            logger.error("error:{}" ,e);
            return  NBIUtils.createErrorResponseHolder(7,e.getMessage());
        }
    }

    @PostMapping(value = "/paymentConfirm")
    public NBIResponseHolder confirmPayment(@RequestBody NBIRequestHolder requestHolder)
    {
        logger.info("start invoking confirmPayment");
        preCheck(requestHolder);
        String json = requestHolder.getData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            NBIPayment o = mapper.readerFor(NBIPayment.class).readValue(json);
            Boolean confirmationResult = feeService.deductFeeByThirdPay(o.getTransactionId(),o.getCheckOutTime(),Double.valueOf(o.getPaidAmount()));
            logger.info("end up invoking confirmPayment");
            return NBIUtils.createSuccessResponder(mapper.writeValueAsString(NBIConfirmationResult.builder().confirmationResult(confirmationResult).build()));
        } catch (IOException e) {
            logger.error("error:{}" ,e);
            return  NBIUtils.createErrorResponseHolder(7,e.getMessage());
        } catch (Exception e) {
            logger.error("error:{}" ,e);;
            return  NBIUtils.createErrorResponseHolder(7,e.getMessage());
        }

    }

}
