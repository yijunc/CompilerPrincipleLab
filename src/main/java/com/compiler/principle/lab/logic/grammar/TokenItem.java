package com.compiler.principle.lab.logic.grammar;

public class TokenItem {

    private Token mToken;

    private Object mValue;

    private int mColumn;

    private int mRow;

    public TokenItem(Token token, Object value,int row,int column) {
        mToken = token;
        mValue = value;
        mColumn = column;
        mRow = row;
    }

    public Token getToken() {
        return mToken;
    }

    public int getColumn() {
        return mColumn;
    }

    public void setColumn(int column) {
        mColumn = column;
    }

    public int getRow() {
        return mRow;
    }

    public void setRow(int row) {
        mRow = row;
    }

    public void setToken(Token token) {
        mToken = token;
    }

    public Object getValue() {
        return mValue;
    }

    public void setValue(Object value) {
        mValue = value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null || !(obj instanceof TokenItem))return false;
        TokenItem tokenItem = (TokenItem)obj;
        return tokenItem.mToken.equals(this.mToken)&&tokenItem.mValue.equals(this.mValue);
    }

    @Override
    public String toString() {
        return "TokenItem{" +
                "mToken=" + mToken +
                ", mValue=" + mValue +
                ", mColumn=" + mColumn +
                ", mRow=" + mRow +
                '}';
    }
}
