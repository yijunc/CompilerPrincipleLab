package com.compiler.principle.lab.logic.grammar;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LexAnalyser {
    private File lexSrcFile;
    private File mFile;

    private String mErrorMessage = "";
    private boolean mHasError = false;

    public boolean hasError() {
        return mHasError;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public LexAnalyser(File lexSrcFile, File sourceFile) {
        this.lexSrcFile = lexSrcFile;
        this.mFile = sourceFile;
    }

    public List<TokenItem> analyse() throws Exception {
        ArrayList<TokenItem> arrayList = new ArrayList<>();
        FileReader reader = new FileReader(mFile);
        Process process = Runtime.getRuntime().exec(lexSrcFile.getParent() + "/lexyyc");
        OutputStream os = process.getOutputStream();
        int b;
        while ((b = reader.read()) != -1) {
            os.write(b);
        }
        os.flush();
        os.close();
        InputStream is = process.getInputStream();
        Scanner scanner = new Scanner(is);
        while (scanner.hasNext()) {
            String type = scanner.next();
            String value = scanner.next();
            int row = scanner.nextInt();
            int column = scanner.nextInt();
            TokenItem item = new TokenItem(new Token(type), value, row, column);
            arrayList.add(item);
        }
        Scanner errorScanner = new Scanner(process.getErrorStream());
        StringBuilder errorMsg = new StringBuilder();
        while (errorScanner.hasNextLine()) {
            errorMsg.append(errorScanner.nextLine());
            errorMsg.append('\n');
        }
        process.waitFor();
        if (process.exitValue() != 0) {
            mHasError = true;
            mErrorMessage = errorMsg.toString();
        } else {
            mHasError = false;
        }
        return arrayList;
    }

}
