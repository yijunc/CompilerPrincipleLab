package com.compiler.principle.lab.logic.grammar;

import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GrammarAnalyzer {

    private static String seperator = "======================================================================";

    public static GrammarResponse analyze(String sourceCode){
        GrammarResponse ret = new GrammarResponse();
        System.out.println(seperator);
        System.out.println("Reading production rules...");
        ProductionFactory factory = new ProductionFactory();
        List<Production> productions;
        try {
            productions = factory.fromFile(ResourceUtils.getFile("classpath:grammar/grammar_rules.txt"));
        } catch (Exception e){
            ret.setStatus("system error");
            ret.setError("Unable to load grammar rules.");
            return ret;
        }
        System.out.println("Productions read.");
        System.out.println("Generating parsing table...");
        LLParser parser = new LLParser(productions,factory.getStartSymbol());
        ret.setParsingTable(parser.getMTable());
        System.out.println("Parsing table done.");
        first(parser,"program");
        long startTime = System.currentTimeMillis();
        try{
            ret.setGrammaTree(parser.llParse(sourceCode));
        } catch (ParserException e){
            ret.setError(e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        ret.setStatus(String.valueOf(endTime - startTime) + "ms");
        return ret;
    }

    private static void first(LLParser parser,String m){
        parser.first(new Symbol(m));
    }
}
