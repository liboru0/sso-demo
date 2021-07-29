package com.liboru.sso.client.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OkController {

    @RequestMapping
    public String ok(){
        return "sso-client-2 ok...";
    }

}
