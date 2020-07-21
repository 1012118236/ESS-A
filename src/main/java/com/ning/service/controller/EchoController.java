package com.ning.service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2020/7/19 17:39
 */
@RestController
public class EchoController {

    @RequestMapping(value = "/echo/{string}",method = RequestMethod.GET)
    public String echo(@PathVariable String string){
        return "Hello Nacos Discovery "+ string;
    }



}
