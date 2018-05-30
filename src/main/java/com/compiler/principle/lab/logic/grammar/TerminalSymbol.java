package com.compiler.principle.lab.logic.grammar;

public class TerminalSymbol extends Symbol {

    private Token mToken;

    public TerminalSymbol(String name, Token token) {
        super(name);
        mToken = token;
    }

    public Token getToken() {
        return mToken;
    }

    public void setToken(Token token) {
        mToken = token;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof TerminalSymbol)) return false;
        TerminalSymbol ts = (TerminalSymbol) obj;
        if (this.isNull() && ts.isNull()) return true;
        if (this.isNull() || ts.isNull()) return false;
        if (this.getName().equals("END") && ts.getName().equals("END")) return true;
        return ts.mToken.equals(this.mToken);
    }

    @Override
    public boolean isNull() {
        return getName().equals("NULL");
    }

    @Override
    public int hashCode() {
        if (mToken == null) return super.hashCode();
        return super.hashCode() ^ mToken.hashCode();
    }

    @Override
    public String toString() {
        return "TerminalSymbol{" +
                "mToken=" + mToken +
                '}';
    }
}