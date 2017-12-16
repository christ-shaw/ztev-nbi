package com.ztev.nbi.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ${xiaozb} on 2017/11/24.
 *
 * @copyright by ztev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NBIOperInfo {
    private String operID;
    private String desc;
}
