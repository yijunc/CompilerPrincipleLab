package com.compiler.principle.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/lex")
public class LexController {

    @RequestMapping("/analyze")
    @ResponseBody
    public Object process(@RequestBody Map<String, Object> payload){
        return payload.get("code");
    }
}
