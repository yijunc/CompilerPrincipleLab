package com.compiler.principle.lab.controller;

import com.compiler.principle.lab.logic.LexResponse;
import com.compiler.principle.lab.logic.LexicalAnalyzer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/lex")
public class LexController {

    @RequestMapping("/")
    public String index() {
        return "Lex Controller";
    }

    @RequestMapping("/analyze")
    public LexResponse process(@RequestBody Map<String, String> payload) {
        try {
            return LexicalAnalyzer.analyze(payload.get("code"));
        } catch (Exception e) {
            return new LexResponse("sys error", null, e.getMessage());
        }

    }
}
