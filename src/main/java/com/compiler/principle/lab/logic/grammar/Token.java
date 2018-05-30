package com.compiler.principle.lab.logic.grammar;

public class Token {
    public Token(String type) {
        mType = type.trim();
    }

    private String mType;

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type.trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Token)) return false;
        Token token = (Token) obj;
        return this.mType.equals(token.mType);
    }

    @Override
    public String toString() {
        return "Token{" + "mType='" + mType + '\'' + '}';
    }

    @Override
    public int hashCode() {
        return mType.hashCode();
    }
}
