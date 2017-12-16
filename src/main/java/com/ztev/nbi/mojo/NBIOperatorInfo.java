package com.ztev.nbi.mojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ${xiaozb} on 2017/11/23.
 *
 * @copyright by ztev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NBIOperatorInfo
{
    private String operatorId;
    private String opMsgSecret;
}
