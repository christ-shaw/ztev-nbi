package com.ztev.nbi.jms;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.ums.ztev.common.mq.MessageType;
import com.zte.ums.ztev.pl.parkinglog.mq.ParkingLogMessage;
import com.zte.ums.ztev.trade.api.ITradeRecordService;
import com.zte.ums.ztev.trade.entity.TradeRecordBO;
import com.ztev.nbi.datagram.NBIReportMsgHolder;
import com.ztev.nbi.mojo.NBICarEntryMsg;
import com.ztev.nbi.mojo.NBICarLeaveMsg;
import com.ztev.nbi.mojo.NBICarLicensePlateUpdateInfo;
import com.ztev.nbi.mojo.NBIParkBill;
import com.ztev.nbi.util.NBIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import com.ztev.py.mq.reports.ParkingProductionInfo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

;import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;


@Component
public class ParkingLogListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingLogListener.class.getName());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    ITradeRecordService tradeRecordService;

    static ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
             .setDateFormat(new SimpleDateFormat(NBIUtils.DATEFMT));

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    @JmsListener(destination = MessageType.TOPIC_PARKINGLOG, containerFactory = "jmsListenerTopicContainerFactory")
    public void onMessage(Message message) {
        LOGGER.info("Receiving message");
        ParkingLogMessage logMessage = getFromJMSMsg(message, ParkingLogMessage.class);

        if (logMessage != null) {
            processLogMessage(logMessage);
        } else {
            LOGGER.warn("logMessage为NULL");
        }
    }


    /**
     * 处理分发
     *
     * @param logMessage
     * @throws Exception void
     */
    private void processLogMessage(ParkingLogMessage logMessage) {
        switch (logMessage.getLogStage()) {
            case LOG_STAGE_IN:
                processIn(logMessage);
                break;
            case LOG_STAGE_LEAVE:
                processLeave(logMessage);
                break;
            default:
                LOGGER.warn("Error LOG Stage = " + logMessage.getLogStage());
                break;
        }
    }

    private void processLeave(ParkingLogMessage logMessage) {
        LOGGER.info("Begin process Out");
        LOGGER.info("TransactionId=" + logMessage.getTransactionId());
        NBICarLeaveMsg msg = NBICarLeaveMsg.builder().transactionId(logMessage.getTransactionId())
                .leaveTime(NBIUtils.getDateFromString(NBIUtils.DATEFMT, logMessage.getTime())).exitName(logMessage.getEntrance())
                .licencePlate(logMessage.getLicencePlate()).stationaryId(logMessage.getStationOid())
                .userType(logMessage.getUserType().getUserType()).build();

        try {
            String data = objectMapper.writeValueAsString(msg);
            sendJmsMsg(MsgType.LEAVE, data);
            LOGGER.info("end send jms msg  process leave");
        } catch (JsonProcessingException e) {
            LOGGER.warn("jms msg aborted");
            LOGGER.error(e.getMessage());
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }

        // not any bill info reported ,we need to query it by ourselves
        int count = 0;
        while (++count < 4) {
            try {
                Thread.sleep(5000);
                TradeRecordBO bo = tradeRecordService.queryTradeRecordByParkTransID((msg.getTransactionId()));
                if (bo != null)
                {
                    NBIParkBill bill = NBIParkBill.builder().amount(BigDecimal.valueOf(bo.getTradeAmt())).licencePlate(msg.getLicencePlate()).transactionId(bo.getParkTransID())
                            .stationaryId(msg.getStationaryId()).paymentTime(bo.getTradeTime()).paidState(Integer.valueOf(bo.getTradeStatus())).build();

                    String data = objectMapper.writeValueAsString(bill);
                    sendJmsMsg(MsgType.BILLING, data);
                    LOGGER.info(" send bill message successfully");
                    break;
                }

            } catch (InterruptedException e) {
               LOGGER.error(e.getMessage());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            } catch (JMSException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
        }
        if (count ==4)
        {
            LOGGER.warn("discard bill message due to  trade record being not found");
        }

    }
    private void sendJmsMsg(MsgType msgType, String data) throws JsonProcessingException,JMSException {
        NBIReportMsgHolder holder = NBIUtils.createReportHolder(data, msgType.getStage());
        LOGGER.info("attempt to send jms message");
        LOGGER.info("type = " + holder.getMsgType());
        LOGGER.info("data=" + data);
        jmsTemplate.convertAndSend(objectMapper.writeValueAsString(holder));
    }

    /**
     * 入场处理，插入新停车日志
     *
     * @param logMessage void
     */
    private void processIn(ParkingLogMessage logMessage) {
        LOGGER.info("Begin process In");
        LOGGER.info("TransactionId=" + logMessage.getTransactionId());

        NBICarEntryMsg msg = NBICarEntryMsg.builder().transactionId(logMessage.getTransactionId())
                .enterTime(NBIUtils.getDateFromString(NBIUtils.DATEFMT, logMessage.getTime())).entranceName(logMessage.getEntrance())
                .licenceColor(logMessage.getLicenceColor()).licencePlate(logMessage.getLicencePlate()).stationaryId(logMessage.getStationOid())
                .userType(logMessage.getUserType().getUserType()).build();

        try {
            String data = objectMapper.writeValueAsString(msg);
            sendJmsMsg(MsgType.ENTER , data);
        } catch (JsonProcessingException e) {
            LOGGER.warn("jms msg aborted");
            LOGGER.error(e.getMessage());
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("end send jms msg  process In");

    }


    // 监听车牌变更信息
    @JmsListener(destination = "park.vehicle.modify-license", containerFactory = "jmsListenerTopicContainerFactory")
    public void onTopicMsg(Message message) throws Exception {
        LOGGER.info("receive a topic message");
        ParkingProductionInfo msg = getFromJMSMsg(message, ParkingProductionInfo.class);
        if (msg == null) {
            LOGGER.warn("ParkingProductionInfo message = null");
            LOGGER.error("Sending JMS is aborted");
            return;
        }

        NBICarLicensePlateUpdateInfo updateInfo =  NBICarLicensePlateUpdateInfo.builder().updatedLicense(msg.getLicenseNo())
                .transactionId(msg.getTransactionNo()).build();


        String data = objectMapper.writeValueAsString(updateInfo);
        sendJmsMsg(MsgType.LICENSEUPDATE, data);
    }

    private <T> T getFromJMSMsg(Message message, Class<T> classType) {
        T infoMsg = null;
        try {
            if (message instanceof TextMessage) {
                String json = ((TextMessage) message).getText();
                LOGGER.info("message json = " + json);

                infoMsg = objectMapper.readerFor(classType).readValue(json);
            } else if (message instanceof ObjectMessage) {
                infoMsg = (T) ((ObjectMessage) message).getObject();
            }
        } catch (JMSException e) {
            LOGGER.error(e.getMessage());
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return infoMsg;
    }
}
