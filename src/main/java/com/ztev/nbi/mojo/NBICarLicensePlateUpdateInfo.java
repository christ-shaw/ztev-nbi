package com.ztev.nbi.mojo;

import lombok.Builder;
import lombok.Data;

/**
 * Created by ${xiaozb} on 2017/12/1.
 *
 * @copyright by ztev
 */
@Data
@Builder
public class NBICarLicensePlateUpdateInfo {
    private String transactionId;
    private String updatedLicense;
}
