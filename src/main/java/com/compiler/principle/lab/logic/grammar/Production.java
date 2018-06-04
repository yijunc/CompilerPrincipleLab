package com.compiler.principle.lab.logic.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Production {

    private Symbol mLeft;
    private List<Symbol> mRight;

    public Production(Symbol left, Symbol[] right) {
        mLeft = left;
        mRight = new ArrayList<>();
        mRight.addAll(Arrays.asList(right));
    }

    public Production(Symbol left, List<Symbol> right) {
        mLeft = left;
        mRight = new ArrayList<>();
        mRight.addAll(right);
    }

    public Symbol getLeft() {
        return mLeft;
    }

    public void setLeft(Symbol left) {
        mLeft = left;
    }

    public List<Symbol> getRight() {
        return mRight;
    }

    public void setRight(Symbol[] right) {
        mRight = new ArrayList<>(right.length);
        mRight.addAll(Arrays.asList(right));
    }

    public void setRight(List<Symbol> right) {
        mRight = new ArrayList<>(right.size());
        mRight.addAll(right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Production that = (Production) o;
        if (!this.mLeft.equals(that.mLeft)) return false;
        if (this.mRight.size() == that.mRight.size()) return false;
        for (int i = 0; i < mRight.size(); i++) {
            if (!this.mRight.get(i).equals(that.mRight.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = mLeft != null ? mLeft.hashCode() : 0;
        result = 31 * result + (mRight != null ? mRight.hashCode() : 0);
        return result;
    }

    public Production copy(){
        List<Symbol> copy = new ArrayList<>();
        copy.addAll(mRight);
        return new Production(mLeft,copy);
    }

    @Override
    public String toString() {
        return "Production{" +
                "mLeft=" + mLeft +
                ", mRight=" + mRight +
                '}';
    }
}
