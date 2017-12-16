package com.ztev.nbi.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Created by ${xiaozb} on 2017/12/15.
 *
 * @copyright by ztev
 */

@Controller

public class HomeController
{
    private Logger logger = LoggerFactory.getLogger(HomeController.class.getName());

    @GetMapping(value ={"/home","/"})
    public String home()
    {
        return "home";
    }

}
