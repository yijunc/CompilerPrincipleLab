package com.compiler.principle.lab.logic.grammar;

import java.util.HashMap;
import java.util.List;

public class GrammarResponse {
    private List<String> symbols;
    private List<String> terminalSymbols;
    private HashMap<String, HashMap<String , String>> parsingTable;
    private SymbolTreeNode grammaTree;
    private String status;
    private String error;

    public GrammarResponse() {
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }

    public List<String> getTerminalSymbols() {
        return terminalSymbols;
    }

    public void setTerminalSymbols(List<String> terminalSymbols) {
        this.terminalSymbols = terminalSymbols;
    }

    public HashMap<String, HashMap<String, String>> getParsingTable() {
        return parsingTable;
    }

    public void setParsingTable(HashMap<String, HashMap<String, String>> parsingTable) {
        this.parsingTable = parsingTable;
    }

    public SymbolTreeNode getGrammaTree() {
        return grammaTree;
    }

    public void setGrammaTree(SymbolTreeNode grammaTree) {
        this.grammaTree = grammaTree;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
