package com.ztev.nbi.mojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ztev.nbi.mojo.seralizer.CustomDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/12/4.
 *
 * @copyright by ztev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NBIPayment
{
   private Double paidAmount;
   private String transactionId;
    @JsonDeserialize(using = CustomDateDeserializer.class)
   private Date checkOutTime;
}


