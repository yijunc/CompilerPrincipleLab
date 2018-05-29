package com.compiler.principle.lab.logic.grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ProductionFactory {
    public List<Production> fromFile(File file) {
        try {
            Set<String> terminals = new HashSet<>();
            List<Production> list = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            addTerminals(terminals, reader.readLine());
            mStartSymbol = new Symbol(reader.readLine());
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("to");
                String left = split[0];
                String right = split[1];
                String[] rightSplit = right.split("\\|");
                for (String rightItem : rightSplit) {
                    Scanner scanner = new Scanner(rightItem);
                    ArrayList<Symbol> symbols = new ArrayList<>();
                    while (scanner.hasNext()) {
                        String s = scanner.next();
                        Symbol symbol;
                        if (terminals.contains(s)) {
                            Token token = new Token(s);
                            symbol = new TerminalSymbol(s, token);
                        } else if (s.equals("NULL")) {
                            symbol = new TerminalSymbol("NULL", null);
                        } else {
                            symbol = new Symbol(s);
                        }
                        symbols.add(symbol);
                    }
                    list.add(new Production(new Symbol(left), symbols));
                }
            }
            reader.close();
            return list;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Symbol getStartSymbol() {
        return mStartSymbol;
    }

    private Symbol mStartSymbol;


    private void addTerminals(Set<String> terminals, String src) {
        Scanner scanner = new Scanner(src);
        while (scanner.hasNext()) {
            terminals.add(scanner.next());
        }
    }
}
