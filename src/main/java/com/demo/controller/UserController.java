package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public String user () {
        return "You can only access this if you have ROLE_USER.";
    }

    @GetMapping("/admin")
    public String admin () {
        return "You can only access this if you have ROLE_ADMIN.";
    }

}
