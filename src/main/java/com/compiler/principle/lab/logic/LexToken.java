package com.compiler.principle.lab.logic;


public class LexToken {
    public String tokenType;
    public String attributeValue;
    public String lineNumber;
    public String linePosition;

    LexToken(String a, String b, String c, String d) {
        tokenType = a;
        attributeValue = b;
        lineNumber = c;
        linePosition = d;
    }

}

