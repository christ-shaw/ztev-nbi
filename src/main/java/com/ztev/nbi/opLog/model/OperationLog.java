package com.ztev.nbi.opLog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ${xiaozb} on 2017/11/28.
 *
 * @copyright by ztev
 */
@Entity
@Table(name="ztev_nbi_operatonlog")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog
{
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;
   private String remoteAddress;
   private Date timeStamp;
   private String method;
}
