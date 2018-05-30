package com.compiler.principle.lab.logic.grammar;

public class GrammarResponse {
    private MTable parsingTable;
    private SymbolTreeNode grammaTree;
    private String status;
    private String error;

    public GrammarResponse() {
    }

    public MTable getParsingTable() {
        return parsingTable;
    }

    public void setParsingTable(MTable parsingTable) {
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
