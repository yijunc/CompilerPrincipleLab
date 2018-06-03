package com.compiler.principle.lab.logic.grammar;

public class ParserException extends Exception {
    public ParserException() {
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }

    public ParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private TokenItem mTokenItem;
    private Symbol mSymbol;

    public TokenItem getmTokenItem() {
        return mTokenItem;
    }

    public void setmTokenItem(TokenItem mTokenItem) {
        this.mTokenItem = mTokenItem;
    }

    public Symbol getmSymbol() {
        return mSymbol;
    }

    public void setmSymbol(Symbol mSymbol) {
        this.mSymbol = mSymbol;
    }

    public TokenItem getTokenItem() {
        return mTokenItem;
    }

    public void setTokenItem(TokenItem tokenItem) {
        mTokenItem = tokenItem;
    }

    public Symbol getSymbol() {
        return mSymbol;
    }

    public void setSymbol(Symbol symbol) {
        mSymbol = symbol;
    }
}