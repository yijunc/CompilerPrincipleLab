package com.compiler.principle.lab.logic.grammar;

import org.springframework.util.ResourceUtils;

import java.util.List;

public class GrammarAnalyzer {

    private static String seperator = "======================================================================";

    public static SymbolTreeNode analyze(String sourceCode) throws Exception{
        System.out.println(seperator);
        System.out.println("Reading production rules...");
        ProductionFactory factory = new ProductionFactory();
        List<Production> productions = factory.fromFile(ResourceUtils.getFile("classpath:grammar/grammar_rules.txt"));
        System.out.println(productions);
        System.out.println("Productions read.");
        System.out.println("Generating parsing table...");
        LLParser parser = new LLParser(productions,factory.getStartSymbol());
        MTable table = parser.getMTable();
        System.out.println(table);
        System.out.println("Parsing table done.");
        first(parser,"program");
        try {
            SymbolTreeNode node = parser.llParse(sourceCode);
            return node;
        }catch (LLParser.ParserException e){
            e.printStackTrace();
        }
        return null;
    }

    private static void first(LLParser parser,String m){
        System.out.println(parser.first(new Symbol(m)));
    }
}
