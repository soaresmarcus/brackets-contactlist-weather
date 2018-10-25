package com.bravi.prova.controllers;

import com.bravi.prova.utils.BracketsValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Home {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
