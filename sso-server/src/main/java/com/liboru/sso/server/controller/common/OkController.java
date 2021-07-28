package com.liboru.sso.server.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OkController {

    @RequestMapping
    public String ok(){
        return "sso-server ok...";
    }

}
