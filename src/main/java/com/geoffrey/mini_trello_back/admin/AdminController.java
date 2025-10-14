package com.geoffrey.mini_trello_back.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    @GetMapping("")
    public String testAdmin(@RequestParam("test") String param) {
        String a = "bonjour";
        return param;
    }
}
