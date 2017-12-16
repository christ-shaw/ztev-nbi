package com.ztev.nbi.opLog.controller;

import com.ztev.nbi.opLog.model.OperationLog;
import com.ztev.nbi.opLog.repository.OperationLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ${xiaozb} on 2017/11/28.
 *
 * @copyright by ztev
 */
@RestController
@RequestMapping("/admin")
public class OperationLogController
{
    @Autowired
    OperationLogRepo repo;

    @GetMapping(value = "/logs")
    public List<OperationLog> getAllOpLogsByAddress(@RequestParam(value = "remoteAddress",required = false) String ipAddress,
                                                    @RequestParam(value = "method",required = false) String method)
    {
       if (ipAddress != null && method != null)
       {
           return repo.findAllByRemoteAddressAndMethod(ipAddress,method);
       }
       else if (ipAddress != null && method == null)
       {
           return repo.findAllByRemoteAddress(ipAddress);
       }
       else
       {
           return repo.findAllByMethod(method);
       }

    }


}
