package com.compiler.principle.lab.logic.lex;

import java.util.List;

public class LexResponse {
    String status;
    List<LexToken> tokens;
    String error;

    public LexResponse() {

    }

    public LexResponse(String status, List<LexToken> tokens, String error) {
        this.status = status;
        this.tokens = tokens;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public LexResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public List<LexToken> getTokens() {
        return tokens;
    }

    public LexResponse setTokens(List<LexToken> tokens) {
        this.tokens = tokens;
        return this;
    }

    public String getError() {
        return error;
    }

    public LexResponse setError(String error) {
        this.error = error;
        return this;
    }
}
