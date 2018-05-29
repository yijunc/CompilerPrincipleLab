package com.compiler.principle.lab.controller;

import com.compiler.principle.lab.logic.grammar.GrammarAnalyzer;
import com.compiler.principle.lab.logic.grammar.SymbolTreeNode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/grammar")
public class GrammarController {
    @RequestMapping("/")
    public String index(){
        return "Grammar Controller";
    }

    @RequestMapping("/analyze")
    public SymbolTreeNode analyze(@RequestBody Map<String, String> payload){
        try{
            return GrammarAnalyzer.analyze(payload.get("code"));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
