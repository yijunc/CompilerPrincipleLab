package com.compiler.principle.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController{

    @RequestMapping("/lexpage")
    public String getLexPage(){
        return "lexpage";
    }

}
