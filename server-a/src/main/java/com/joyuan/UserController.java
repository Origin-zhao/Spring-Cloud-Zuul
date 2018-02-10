package com.joyuan;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    public String info(){
        return "joyaun";
    }
}
