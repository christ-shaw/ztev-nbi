package com.ztev.nbi.exception;

import com.ztev.nbi.util.NBIUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by ${xiaozb} on 2017/11/21.
 *
 * @copyright by ztev
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = SignatureIncompltedException.class)
    public ResponseEntity exception(SignatureIncompltedException exception) {
        return  NBIUtils.send( 5,"signature is incomplete");
    }


}
