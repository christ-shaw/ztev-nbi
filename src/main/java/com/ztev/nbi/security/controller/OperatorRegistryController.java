package com.ztev.nbi.security.controller;

import com.ztev.nbi.exception.OperatorDuplicatedException;
import com.ztev.nbi.mojo.NBIOperatorInfo;
import com.ztev.nbi.security.NBIOperInfo;
import com.ztev.nbi.security.RegistryInfo;
import com.ztev.nbi.security.repository.OperatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ${xiaozb} on 2017/11/24.
 *
 * @copyright by ztev
 */
@RestController
public class OperatorRegistryController
{
    private static Logger logger = LoggerFactory.getLogger(OperatorRegistryController.class.getName());
   @Autowired
    OperatorRepository repository;
    @PostMapping("/sign-up")
    public void signUp(@RequestBody NBIOperInfo operatorInfo) throws OperatorDuplicatedException
    {
        RegistryInfo existedOne = repository.findByOperatorId(operatorInfo.getOperID());
        if (existedOne != null)
        {
              throw new OperatorDuplicatedException("duplicated operator " + operatorInfo.getOperID() );
        }
        else
        {
            RegistryInfo info = RegistryInfo.builder().description(operatorInfo.getDesc()).secretKey(RandomStringUtils.random(16, true, true))
                    .operatorId(operatorInfo.getOperID()).build();

            repository.save(info);
            logger.info("save {} successfully" + info.getOperatorId() );
        }


    }

}
