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
        String command = "gcc -o " + lexSrcFile.getParent() + "\\lexyyc" + " " + lexSrcFile.getAbsolutePath();
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
        System.out.println(lexSrcFile.getParent() + "\\" + UUID.randomUUID().toString());
        sourceFile = new File(lexSrcFile.getParent() + "\\" + UUID.randomUUID().toString());
        FileWriter writer = new FileWriter(sourceFile);
        writer.write(sourceCode);
        writer.flush();
        writer.close();
    }

    public static List<LexToken> lexAnalyzer(String src) throws Exception{
        List<LexToken> ret = new ArrayList<>();
        String line;
        File srcFile = new File(src);
        String fileName = srcFile.getName();
        fileName = fileName.substring(0,
                fileName.lastIndexOf(".") == -1 ? fileName.length() : fileName.lastIndexOf("."));
        String formatStr = "%-20s %-25s %-15s %-15s";
        long startTime = System.currentTimeMillis();
        List<String> command = new ArrayList<>();
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            command.add("cmd");
            command.add("/c");
            command.add(".\\" + lexSrcFile.getParent() + "\\lexyyc" + ".exe < " + src);
        } else {
            throw new Exception("Unsupported Operating System.");
        }
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process ps = processBuilder.start();
        // System.out.println(command);
        BufferedInputStream inputStream = new BufferedInputStream(ps.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        File tokenedFile = new File(fileName + ".tok");
        FileWriter fw = new FileWriter(tokenedFile);
        BufferedWriter writer = new BufferedWriter(fw);
        while ((line = br.readLine()) != null) {
            String[] tokenInfo = line.split("\t+");
            // for(int i = 0; i < tokenInfo.length;i++){
            // System.out.println(tokenInfo[i]);
            // }
            System.out.println(String.format(formatStr, tokenInfo[0], tokenInfo[1], tokenInfo[2], tokenInfo[3]));
            ret.add(new LexToken(tokenInfo[0], tokenInfo[1], tokenInfo[2], tokenInfo[3]));
            writer.write(String.format(formatStr, tokenInfo[0], tokenInfo[1], tokenInfo[2], tokenInfo[3]));
            writer.newLine();
        }

        boolean hasError = false;
        BufferedInputStream errorStream = new BufferedInputStream(ps.getErrorStream());
        BufferedReader ebr = new BufferedReader(new InputStreamReader(errorStream));
        while ((line = ebr.readLine()) != null) {
            System.out.println(line);
            hasError = true;
            writer.write(line);
            writer.newLine();
        }

        writer.flush();
        writer.close();
        fw.close();
        ps.waitFor();

        if (hasError) {
            throw new Exception("Lex Error In Source File: " + src);
        }

        long endTime = System.currentTimeMillis();
        System.out.printf("Lexical Analyzed Source File: " + src + " in %d ms.\n", endTime - startTime);
        System.out.println(seperator);
        return ret;
    }

    public static List<LexToken> analyze(String sourceCode) throws Exception {
        compileLexConfig();
        saveSourceFile(sourceCode);

        return null;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }
}


