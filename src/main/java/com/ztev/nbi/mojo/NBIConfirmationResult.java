package com.ztev.nbi.mojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ${xiaozb} on 2017/12/4.
 *
 * @copyright by ztev
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NBIConfirmationResult
{
    Boolean confirmationResult;
}
