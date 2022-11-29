package com.tying.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tying
 * @version 1.0
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    //@PreAuthorize("hasAuthority('user:likes:index1')")
    @PreAuthorize("@security_ex.hasAuthority('user:likes:index')")
    public String test() {
        return "success";
    }
}
