package com.compiler.principle.lab.logic;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LexicalAnalyzer {

    private static File lexSrcFile;

    private static File sourceFile;

    private static String seperator = "======================================================================";

    public static void compileLexConfig() throws Exception {
        lexSrcFile = ResourceUtils.getFile("classpath:lex.yy.c");
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

    public static void saveSourceFile(String sourceCode) throws Exception {
        System.out.println("Saving Files...");
        sourceFile = new File(lexSrcFile.getParent() + "/sourceCode.toy");
        System.out.println(sourceFile.getAbsolutePath());
        FileWriter writer = new FileWriter(sourceFile);
        writer.write(sourceCode);
        writer.flush();
        writer.close();
    }

    public static LexResponse lexAnalyzer() throws Exception {
        List<LexToken> retToken = new ArrayList<>();
        LexResponse ret = new LexResponse();
        String line;
        long startTime = System.currentTimeMillis();
        List<String> command = new ArrayList<>();
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("linux")) {
            command.add("sh");
            command.add("-c");
            command.add(lexSrcFile.getParent() + "/lexyyc" + " < " + sourceFile.getAbsolutePath());
        } else {
            throw new Exception("Unsupported Operating System.");
        }
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process ps = processBuilder.start();
        BufferedInputStream inputStream = new BufferedInputStream(ps.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        while ((line = br.readLine()) != null) {
            String[] tokenInfo = line.split("\t+");
            for (int i = 0; i < tokenInfo.length; i++) {
                System.out.print(tokenInfo[i] + " ");
            }
            System.out.println();
            retToken.add(new LexToken(tokenInfo[0], tokenInfo[1], tokenInfo[2], tokenInfo[3]));
        }

        ret.setTokens(retToken);

        boolean hasError = false;
        BufferedInputStream errorStream = new BufferedInputStream(ps.getErrorStream());
        BufferedReader ebr = new BufferedReader(new InputStreamReader(errorStream));
        while ((line = ebr.readLine()) != null ) {
            ret.setError(line);
            System.out.println(line);
            hasError = true;
        }
        ps.waitFor();
        long endTime = System.currentTimeMillis();

        if (hasError) {
            ret.setStatus("lex error");
        }
        else{
            ret.setStatus(String.valueOf(endTime - startTime) + "ms");
        }
        
        System.out.printf("Lexical Analyzed Source File: " + sourceFile.getAbsolutePath() + " in %d ms.\n", endTime - startTime);
        System.out.println(seperator);
        return ret;
    }

    public static LexResponse analyze(String sourceCode) throws Exception {
        compileLexConfig();
        saveSourceFile(sourceCode);
        return lexAnalyzer();
    }
}


