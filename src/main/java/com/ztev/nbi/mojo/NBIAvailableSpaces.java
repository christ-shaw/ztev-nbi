package com.ztev.nbi.mojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
@Data
@Builder
public class NBIAvailableSpaces
{
    @JsonProperty("availableSpaces")
    private int availableSpaces;
}
