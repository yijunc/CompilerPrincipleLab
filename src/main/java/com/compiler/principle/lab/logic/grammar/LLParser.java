package com.compiler.principle.lab.logic.grammar;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class LLParser {


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

    private List<Production> mProductions;

    private HashMap<Symbol, Boolean> mCanEmpty = new HashMap<>();
    private Symbol mStartSymbol;
    private Map<Symbol, Set<Symbol>> mFirst = new HashMap<>();
    private Map<Symbol, Set<TerminalSymbol>> mFollow = new HashMap<>();

    private MTable mMTable = new MTable();

    private static final TerminalSymbol NULL_SYMBOL = new TerminalSymbol("NULL", null);
    private static final TerminalSymbol END_SYMBOL = new TerminalSymbol("END", null);

    public LLParser(List<Production> productions, Symbol startSymbol) {
        mProductions = productions;
        mStartSymbol = startSymbol;
        parse();
        initMTable();
    }

    public MTable getMTable() {
        return mMTable;
    }

    public String getLexError() {
        return mLexError;
    }

    private String mLexError;

    private boolean mLexHasError = false;

    public boolean lexError() {
        return mLexHasError;
    }

    private File lexSrcFile;

    private File sourceFile;

    private void compileLexConfig() throws Exception {
        lexSrcFile = ResourceUtils.getFile("classpath:grammar/lex.yy.c");
        String command = "gcc -o " + lexSrcFile.getParent() + "/lexyyc" + " " + lexSrcFile.getAbsolutePath();
        System.out.println("Compiling Lex config...");
        System.out.println(command);
        Process ps = Runtime.getRuntime().exec(command);
        ps.waitFor();
        if (ps.exitValue() == 0) {
            System.out.println("Compile Lex Config Succeed.");
        } else {
            throw new Exception("Lexical Configeration Compile ERROR.");
        }
    }

    private void saveSourceFile(String sourceCode) throws Exception {
        System.out.println("Saving Files...");
        sourceFile = new File(lexSrcFile.getParent() + "/sourceCode.toy");
        System.out.println(sourceFile.getAbsolutePath());
        FileWriter writer = new FileWriter(sourceFile);
        writer.write(sourceCode);
        writer.flush();
        writer.close();
    }

    public SymbolTreeNode llParse(String sourceCode) throws ParserException {
        try{
            compileLexConfig();
            saveSourceFile(sourceCode);
        } catch (Exception e){
            throw new ParserException("ERROR: System error.");
        }

        SymbolTreeNode head = new SymbolTreeNode(mStartSymbol);
        LexAnalyser analyser = new LexAnalyser(lexSrcFile, sourceFile);
        List<TokenItem> tokenItems = analyser.analyse();

        List<TerminalSymbol> symbols = new ArrayList<>();
        tokenItems.stream().map(t -> new TerminalSymbol(t.getToken().getType(), t.getToken())).forEach(symbols::add);
        symbols.add(END_SYMBOL);
        if (analyser.hasError()) {
            mLexError = analyser.getErrorMessage();
            mLexHasError = true;
        }
        recurGenerateTree(head, symbols, tokenItems, 0);
        return head;
    }

    private int recurGenerateTree(SymbolTreeNode head, List<TerminalSymbol> list, List<TokenItem> tokenItems, int index) throws ParserException {
        Symbol symbol = head.getHead();
        if (index >= list.size()) {
            ParserException pe = new ParserException("ERROR: Syntax error.");
            pe.setTokenItem(tokenItems.get(index));
            pe.setSymbol(symbol);
            throw pe;
        }
        TerminalSymbol terminalSymbol = list.get(index);
        if (symbol instanceof TerminalSymbol) {
            if (symbol.equals(NULL_SYMBOL)) return 0;
            if (!symbol.equals(terminalSymbol)) {
                ParserException pe = new ParserException("ERROR: Syntax error. ");
                pe.setSymbol(symbol);
                pe.setTokenItem(tokenItems.get(index));
                throw pe;
            }
            return 1;
        }
        ArrayList<Production> productions = mMTable.get(symbol, terminalSymbol);
        if (productions.size() != 1) {
            ParserException pe = new ParserException("ERROR: Syntax error.");
            pe.setTokenItem(tokenItems.get(index));
            pe.setSymbol(symbol);
            throw pe;
        }
        Production production = productions.get(0);
        List<Symbol> rightSymbols = production.getRight();
        for (Symbol s : rightSymbols) {
            SymbolTreeNode stn = new SymbolTreeNode(s);
            if (s instanceof TerminalSymbol) {
                stn.setValue(tokenItems.get(index).getValue());
            }
            head.addKid(stn);
        }
        int sum = 0;
        for (SymbolTreeNode node : head.getKids()) {
            int countTerminals = recurGenerateTree(node, list, tokenItems, index + sum);
            sum += countTerminals;
        }
        return sum;
    }

    private void initMTable() {
        for (Production p : mProductions) {
            Set<Symbol> alpha = first(p.getRight());
            alpha.stream().filter(k -> k instanceof TerminalSymbol).map(k -> (TerminalSymbol) k).forEach(k -> {
                mMTable.add(p.getLeft(), k, p);
            });

            if (alpha.contains(NULL_SYMBOL)) {
                Set<TerminalSymbol> set = follow(p.getLeft());
                set.forEach(k -> mMTable.add(p.getLeft(), k, p));

                if (set.contains(END_SYMBOL)) {
                    mMTable.add(p.getLeft(), END_SYMBOL, p);
                }
            }
        }
    }

    private void parse() {
        Set<TerminalSymbol> endOnlySet = new HashSet<>();
        endOnlySet.add(new TerminalSymbol("END", null));
        mFollow.put(mStartSymbol, endOnlySet);
        for (Production p : mProductions) {
            List<Symbol> rightSymbolList = p.getRight();
            for (int i = 0; i < rightSymbolList.size() - 1; i++) {
                Symbol symbol = rightSymbolList.get(i);
                if (!mFollow.containsKey(symbol)) {
                    mFollow.put(symbol, new HashSet<>());
                }
                Set<TerminalSymbol> set = mFollow.get(symbol);
                List<Symbol> laterList = rightSymbolList.subList(i + 1, rightSymbolList.size());
                Set<Symbol> fl = first(laterList);
                fl.stream().filter((m) -> (m instanceof TerminalSymbol && (!m.isNull()))).map((m) -> (TerminalSymbol) m).forEach(set::add);
            }
        }
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Production p : mProductions) {
                List<Symbol> rightSymbolList = p.getRight();
                int index = rightSymbolList.size() - 1;
                while (true) {
                    Symbol symbol = rightSymbolList.get(index);
                    if (!mFollow.containsKey(symbol)) {
                        mFollow.put(symbol, new HashSet<>());
                        changed = true;
                    }
                    if (!mFollow.containsKey(p.getLeft())) {
                        mFollow.put(p.getLeft(), new HashSet<>());
                        changed = true;
                    }
                    Set<TerminalSymbol> set = mFollow.get(symbol);
                    Set<TerminalSymbol> addSet = mFollow.get(p.getLeft());
                    int size = set.size();
                    set.addAll(addSet);
                    if (set.size() != size) changed = true;
                    if (!canEmpty(symbol) || index == 0) break;
                    index--;
                }
            }
        }
    }

    public Set<TerminalSymbol> follow(Symbol a) {
        return mFollow.get(a);
    }

    private Set<Symbol> mIsComputingFirst = new HashSet<>();

    public Set<Symbol> first(Symbol a) {
        if (mFirst.containsKey(a)) {
            return mFirst.get(a);
        }
        Set<Symbol> set = new HashSet<>();
        if (a instanceof TerminalSymbol) {
            set.add(a);
            return set;
        }
        mIsComputingFirst.add(a);
        for (Production p : mProductions) {
            if (p.getLeft().equals(a)) {
                int index = 0;
                while (true) {
                    Symbol rightFirstSymbol = p.getRight().get(index);
                    set.add(rightFirstSymbol);
                    if (!mIsComputingFirst.contains(rightFirstSymbol) || mFirst.containsKey(rightFirstSymbol))
                        set.addAll(first(rightFirstSymbol));
                    if (!canEmpty(rightFirstSymbol) || index == p.getRight().size() - 1) break;
                    index++;
                }
            }
        }
        if (canEmpty(a)) {
            set.add(new TerminalSymbol("NULL", null));
        }
        mFirst.put(a, set);
        mIsComputingFirst.remove(a);
        return set;
    }

    public Set<Symbol> first(List<Symbol> a) {
        Set<Symbol> set = new HashSet<>();
        for (Symbol s : a) {
            set.addAll(first(s));
            if (!canEmpty(s)) {
                return set;
            }
        }
        set.add(new TerminalSymbol("NULL", null));
        return set;
    }

    private Set<Symbol> mIsComputingCanEmpty = new HashSet<>();

    private boolean canEmpty(Symbol a) {
        if (a.isNull()) return true;
        if (a.getName().equals("END")) return false;
        if (mCanEmpty.containsKey(a)) return mCanEmpty.get(a);
        mIsComputingCanEmpty.add(a);
        boolean m = false;
        for (Production p : mProductions) {
            if (p.getLeft().equals(a)) {
                boolean b = true;
                List<Symbol> rightSymbols = p.getRight();
                for (Symbol symbol : rightSymbols) {
                    if (mCanEmpty.containsKey(symbol)) {
                        boolean t = mCanEmpty.get(symbol);
                        if (!t) {
                            b = false;
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (mIsComputingCanEmpty.contains(symbol) || !canEmpty(symbol)) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    m = true;
                    break;
                }
            }
        }
        mCanEmpty.put(a, m);
        mIsComputingCanEmpty.remove(a);
        return m;
    }
}
