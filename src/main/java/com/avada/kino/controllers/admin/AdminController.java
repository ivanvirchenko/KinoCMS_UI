package com.avada.kino.controllers.admin;

import com.avada.kino.KinoCmsApp;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String getAdmin() {
        return "admin/index-admin";
    }
}
