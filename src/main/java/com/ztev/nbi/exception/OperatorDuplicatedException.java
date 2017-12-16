package com.ztev.nbi.exception;

/**
 * Created by ${xiaozb} on 2017/11/24.
 *
 * @copyright by ztev
 */
public class OperatorDuplicatedException  extends RuntimeException {
    public  OperatorDuplicatedException(String errorReason)
    {
        super(errorReason);
    }
}
