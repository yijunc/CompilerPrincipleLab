package com.compiler.principle.lab.controller;

import com.compiler.principle.lab.logic.LexResponse;
import com.compiler.principle.lab.logic.LexicalAnalyzer;
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
    public Object process(@RequestBody Map<String, Object> payload) {

        try {
            LexicalAnalyzer.analyze((String) payload.get("code"));
            return new LexResponse("ok", null, "fuck");
        } catch (Exception e) {
            return new LexResponse("error", null, e.getMessage());
        }
        //return LexicalAnalyzer.analyze(new String[]{"classpath:test1.toy"});
    }
}
