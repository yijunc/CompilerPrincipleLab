package com.compiler.principle.lab.logic.grammar;

public class Symbol {
    private String mName;

    public Symbol(String name) {
        mName = name.trim();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name.trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Symbol)) return false;
        Symbol symbol = (Symbol) obj;
        return symbol.mName.equals(this.mName);
    }

    public boolean isNull() {
        return false;
    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }

    @Override
    public String toString() {
        return "Symbol{" + "mName='" + mName + '\'' + '}';
    }
}