package com.ztev.nbi.mojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ztev.nbi.util.NBIUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/23.
 *
 * @copyright by ztev
 */
/*
transactionId
stationaryId
licencePlate
totalAmount
paidstate
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NBIParkBill
{
    private String transactionId;
    private String stationaryId;
    private String licencePlate;
    private BigDecimal amount;
    private String paymentTime;
    private int paidState;
}
