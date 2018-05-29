package com.compiler.principle.lab.logic.grammar;

import java.util.ArrayList;

public class SymbolTreeNode {

    Symbol mHead;

    ArrayList<SymbolTreeNode> mKids;

    public Object getValue() {
        return mValue;
    }

    public void setValue(Object value) {
        mValue = value;
    }

    private Object mValue;

    public SymbolTreeNode(Symbol head) {
        mHead = head;
        mKids = new ArrayList<>();
    }

    public Symbol getHead() {
        return mHead;
    }

    public void setHead(Symbol head) {
        mHead = head;
    }

    public ArrayList<SymbolTreeNode> getKids() {
        return mKids;
    }

    public void setKids(ArrayList<SymbolTreeNode> kids) {
        mKids = kids;
    }

    public void addKid(SymbolTreeNode kids){
        mKids.add(kids);
    }
}
